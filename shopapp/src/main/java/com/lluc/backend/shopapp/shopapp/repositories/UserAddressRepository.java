package com.lluc.backend.shopapp.shopapp.repositories;

import com.lluc.backend.shopapp.shopapp.models.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}