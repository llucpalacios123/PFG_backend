package com.lluc.backend.shopapp.shopapp.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lluc.backend.shopapp.shopapp.models.entities.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByUsername(String username);
}
