package com.lluc.backend.shopapp.shopapp.services.interfaces;
import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.dto.OrderDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.entities.UserAddress;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<UserDTO> findAll();
    
    UserDTO save(User user);
    
    Optional<UserDTO> findById(Long id);
    
    void delete(Long id);

    Optional<UserDTO> findByUsername(String username);

    void changePassword(String username, String oldPassword, String newPassword);

    Optional<User> findByEmail(String email);

    void resendVerificationEmail(User user);

    UserAddress addAddress(String username, UserAddress address);

    void deleteAddress(String username, Long addressId);

    UserAddress updateAddress(String username, Long addressId, UserAddress updatedAddress);
    List<UserAddress> getAddresses(String username);

    List<OrderDTO> getOrderHistory(String username);
}
