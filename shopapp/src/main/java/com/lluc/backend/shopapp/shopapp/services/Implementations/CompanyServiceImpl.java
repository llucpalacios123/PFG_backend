package com.lluc.backend.shopapp.shopapp.services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.dto.CompanyDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.DTOMapperCompany;
import com.lluc.backend.shopapp.shopapp.models.entities.Company;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.request.CompanyRequest;
import com.lluc.backend.shopapp.shopapp.repositories.CompanyRepository;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.CompanyService;

import jakarta.transaction.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;

    @Autowired UsersRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UsersRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Company findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    @Transactional
    public CompanyDTO save(UserDTO userDTO, CompanyRequest companyRequest) {

        
        User user = userRepository.findById(userDTO.getId())
        .orElseThrow(() -> new RuntimeException("User not found"));

        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setDescription(companyRequest.getDescription());
        company.setAdministrator(user);
        company.setEmail(user.getEmail());
        Company companySaved = companyRepository.save(company);
        
        user.setEmpresa(companySaved);
        userRepository.save(user);

        return DTOMapperCompany.toDTO(companySaved);

    }


}
