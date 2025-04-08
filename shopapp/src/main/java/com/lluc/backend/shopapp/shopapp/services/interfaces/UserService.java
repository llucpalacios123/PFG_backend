package com.lluc.backend.shopapp.shopapp.services.interfaces;
import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findAll();
    
    User save(User user);
    
    Optional<User> findById(Long id);
    
    void delete(Long id);
}
