package com.lluc.backend.shopapp.shopapp.services.interfaces;

import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.dto.CompanyDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.Company;
import com.lluc.backend.shopapp.shopapp.models.request.CompanyRequest;


@Service
public interface CompanyService {
    public Company findById(Long id);
    public CompanyDTO save(UserDTO user, CompanyRequest company);
}
