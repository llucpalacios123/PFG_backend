package com.lluc.backend.shopapp.shopapp.services.Implementations;
import com.lluc.backend.shopapp.shopapp.models.User;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
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
    public Optional<User> findById(Long id) {
        return usersRepository.findById(id);
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