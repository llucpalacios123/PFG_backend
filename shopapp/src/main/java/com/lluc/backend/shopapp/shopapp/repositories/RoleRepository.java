package com.lluc.backend.shopapp.shopapp.repositories;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lluc.backend.shopapp.shopapp.models.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
