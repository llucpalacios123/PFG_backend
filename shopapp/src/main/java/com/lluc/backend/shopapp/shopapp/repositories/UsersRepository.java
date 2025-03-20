package com.lluc.backend.shopapp.shopapp.repositories;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.lluc.backend.shopapp.shopapp.models.User;

public interface UsersRepository extends CrudRepository<User, Long> {
    List<User> findAll();
}
