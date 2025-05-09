package com.lluc.backend.shopapp.shopapp.services.interfaces;
import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.User;

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
}
