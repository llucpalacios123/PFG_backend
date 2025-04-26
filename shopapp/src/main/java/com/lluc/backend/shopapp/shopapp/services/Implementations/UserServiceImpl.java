package com.lluc.backend.shopapp.shopapp.services.Implementations;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.DTOMapperUser;
import com.lluc.backend.shopapp.shopapp.models.entities.Role;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.repositories.RoleRepository;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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
    private final UsersRepository usersRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public UserServiceImpl(UsersRepository usersRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {

        List<User> users = usersRepository.findAll();

        // Convertir todos los usuarios a UserDTO usando streams
        List<UserDTO> usersDTO = users.stream()
                                    .map(DTOMapperUser::toDTO) // Access the static method directly
                                    .collect(Collectors.toList());

        return usersDTO;
    }

    @Transactional
    public UserDTO save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        
        List<Role> roles = new ArrayList<>();
        roles.add(role.orElseThrow(() -> new RuntimeException("Role not found")));

        user.setRoles(roles);
        
        return DTOMapperUser.toDTO(usersRepository.save(user));
    }


    @Transactional
    public User save_2(User user) {
        try {
            if (user.getId() == null) {
                user.setVersion(0);
                return usersRepository.save(user); // Inicializa la versión para nuevas entidades
            } else {
                Optional<User> existingUser = usersRepository.findById(user.getId());
                if (existingUser.isPresent()) {
                    User updatedUser = existingUser.get();
                    updatedUser.setUsername(user.getUsername());
                    updatedUser.setEmail(user.getEmail());
                    return usersRepository.save(updatedUser);
                } else {
                    throw new RuntimeException("User not found with id: " + user.getId());
                }
            }
        } catch (OptimisticLockingFailureException e) {
            // Manejo de la excepción de bloqueo optimista
            throw new RuntimeException("Error de bloqueo optimista al guardar el usuario", e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        Optional<User> user = usersRepository.findById(id);
        if(user.isEmpty()){
            throw new RuntimeException("User not found with id: " + id);
        }
        
        return Optional.of(DTOMapperUser.toDTO(user.get()));
    }
    @Transactional
    public void delete(Long id) {
        try {
            usersRepository.deleteById(id);
        } catch (OptimisticLockingFailureException e) {
            // Manejo de la excepción de bloqueo optimista
            throw new RuntimeException("Error de bloqueo optimista al eliminar el usuario", e);
        }
    }
}