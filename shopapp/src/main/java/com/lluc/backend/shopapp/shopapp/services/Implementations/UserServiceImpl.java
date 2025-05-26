package com.lluc.backend.shopapp.shopapp.services.Implementations;

import com.lluc.backend.shopapp.shopapp.auth.JwtTokenProvider;
import com.lluc.backend.shopapp.shopapp.models.dto.OrderDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.UserMapper;
import com.lluc.backend.shopapp.shopapp.models.entities.Role;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.entities.UserAddress;
import com.lluc.backend.shopapp.shopapp.repositories.RoleRepository;
import com.lluc.backend.shopapp.shopapp.repositories.UserAddressRepository;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserAddressRepository userAddressRepository;

    public UserServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = usersRepository.findAll();

        // Convert all users to UserDTO using MapStruct
        return users.stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO save(User user) {
        if (user.getId() == null) {
            // Create a new user
            return createUser(user);
        } else {
            // Update an existing user
            return updateUser(user);
        }
    }

    private UserDTO createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findByName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role.orElseThrow(() -> new RuntimeException("Role not found")));

        user.setRoles(roles);

        User userSaved = usersRepository.save(user);

        List<GrantedAuthority> authorities = roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
        String token = jwtTokenProvider.generateToken(userSaved.getId(), userSaved.getUsername(), authorities, user.getEmpresa() != null, true, 24 * 14);

        emailService.sendRegistrationEmail(user.getEmail(), user.getUsername(), token);

        return UserMapper.INSTANCE.toDTO(userSaved); // Usar MapStruct
    }

    private UserDTO updateUser(User user) {
        User existingUser = usersRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("UserNotFound: " + user.getId()));

        boolean usernameChanged = false;

        if (user.getUsername() != null && !user.getUsername().equals(existingUser.getUsername())) {
            existingUser.setUsername(user.getUsername());
            usernameChanged = true; // Mark that the username has changed
        }
        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        User updatedUser = usersRepository.save(existingUser);

        String newToken = null;

        if (usernameChanged) {
            List<GrantedAuthority> authorities = updatedUser.getRoles()
                    .stream()
                    .map(r -> new SimpleGrantedAuthority(r.getName()))
                    .collect(Collectors.toList());

            newToken = jwtTokenProvider.generateToken(updatedUser.getId(), updatedUser.getUsername(), authorities, user.getEmpresa() != null, false, 2);
        }

        // Convert the updated user to DTO using MapStruct
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(updatedUser);

        if (newToken != null) {
            userDTO.setToken(newToken);
        }

        return userDTO;
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        Optional<User> user = usersRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found with id: " + id);
        }

        return Optional.of(UserMapper.INSTANCE.toDTO(user.get())); // Usar MapStruct
    }

    @Transactional
    public void delete(Long id) {
        try {
            usersRepository.deleteById(id);
        } catch (OptimisticLockingFailureException e) {
            // Handle optimistic locking exception
            throw new RuntimeException("Optimistic locking error while deleting the user", e);
        }
    }

    @Override
    public Optional<UserDTO> findByUsernameDTO(String username) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return Optional.of(UserMapper.INSTANCE.toDTO(user)); // Usar MapStruct
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        // Find the user by username
        Optional<User> userOptional = usersRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("La contraseña antigua es incorrecta");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public void resendVerificationEmail(User user) {
        // Generar un nuevo token de verificación
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), authorities, user.getEmpresa() != null, true, 24 * 14);

        // Crear el enlace de verificación
        emailService.sendRegistrationEmail(user.getEmail(), user.getUsername(), token);
    }

    @Override
    @Transactional
    public UserAddress addAddress(String username, UserAddress address) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        address.setUser(user);
        return userAddressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(String username, Long addressId) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        UserAddress address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));
        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Address does not belong to the authenticated user");
        }
        userAddressRepository.delete(address);
    }

    @Override
    @Transactional
    public UserAddress updateAddress(String username, Long addressId, UserAddress updatedAddress) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        UserAddress existingAddress = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));
        if (!existingAddress.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Address does not belong to the authenticated user");
        }
        existingAddress.setStreet(updatedAddress.getStreet());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setPostalCode(updatedAddress.getPostalCode());
        existingAddress.setCountry(updatedAddress.getCountry());
        return userAddressRepository.save(existingAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAddress> getAddresses(String username) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return user.getAddresses();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrderHistory(String username) {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return user.getOrders().stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setStreet(order.getStreet());
            orderDTO.setCity(order.getCity());
            orderDTO.setState(order.getState());
            orderDTO.setPostalCode(order.getPostalCode());
            orderDTO.setCountry(order.getCountry());

            List<OrderDTO.OrderProductDTO> productDTOs = order.getProducts().stream().map(product -> {
                OrderDTO.OrderProductDTO productDTO = new OrderDTO.OrderProductDTO();
                productDTO.setProductId(product.getProduct().getId());
                productDTO.setOrderProductId(product.getId());
                productDTO.setProductName(product.getProduct().getTranslations().get(0).getName());
                productDTO.setCategory(product.getCategory());
                productDTO.setQuantity(product.getQuantity());
                productDTO.setStatus(product.getStatus());
                productDTO.setImageUrl(product.getProduct().getPhotos().get(0));
                return productDTO;
            }).collect(Collectors.toList());

            orderDTO.setProducts(productDTOs);
            return orderDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}