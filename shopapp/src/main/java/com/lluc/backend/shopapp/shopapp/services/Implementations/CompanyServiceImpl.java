package com.lluc.backend.shopapp.shopapp.services.Implementations;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lluc.backend.shopapp.shopapp.models.dto.CompanyDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.CompanyOrderDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.dto.mapper.CompanyMapper;
import com.lluc.backend.shopapp.shopapp.models.entities.Company;
import com.lluc.backend.shopapp.shopapp.models.entities.OrderProduct;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import com.lluc.backend.shopapp.shopapp.models.request.CompanyRequest;
import com.lluc.backend.shopapp.shopapp.repositories.CompanyRepository;
import com.lluc.backend.shopapp.shopapp.repositories.OrderProductRepository;
import com.lluc.backend.shopapp.shopapp.repositories.UsersRepository;
import com.lluc.backend.shopapp.shopapp.services.interfaces.CompanyService;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {
    
    @Autowired
    CompanyRepository companyRepository;

    @Autowired UsersRepository userRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

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
        // Buscar al usuario por su ID
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        // Mapear los datos de CompanyRequest a la entidad Company usando MapStruct
        Company company = CompanyMapper.INSTANCE.toEntity(companyRequest);
    
        // Establecer el administrador y el email de la compañía
        company.setAdministrator(user);
        company.setEmail(user.getEmail());
    
        // Guardar la compañía en la base de datos
        Company companySaved = companyRepository.save(company);
    
        // Asociar la compañía al usuario
        user.setEmpresa(companySaved);
        userRepository.save(user);
    
        // Convertir la entidad guardada a DTO y devolverla
        return CompanyMapper.INSTANCE.toDTO(companySaved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyOrderDTO> getCompanyOrdersGroupedByOrder(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        Company company = user.getEmpresa();
        if (company == null) {
            throw new RuntimeException("User is not associated with any company");
        }

        // Obtener todos los productos pedidos a la compañía
        List<OrderProduct> orderProducts = orderProductRepository.findByCompany(company);

        // Agrupar los productos por pedido
        Map<String, List<OrderProduct>> groupedByOrder = orderProducts.stream()
                .collect(Collectors.groupingBy(orderProduct -> orderProduct.getOrder().getOrderId()));

        // Convertir los datos agrupados a DTOs
        return groupedByOrder.entrySet().stream().map(entry -> {
            CompanyOrderDTO orderDTO = new CompanyOrderDTO();
            orderDTO.setOrderId(entry.getKey());
            orderDTO.setOrderStatus(entry.getValue().get(0).getOrder().getStatus()); // Estado del pedido

            List<CompanyOrderDTO.OrderProductDTO> productDTOs = entry.getValue().stream().map(orderProduct -> {
                CompanyOrderDTO.OrderProductDTO productDTO = new CompanyOrderDTO.OrderProductDTO();
                ;
                productDTO.setProductId(orderProduct.getId());
                productDTO.setProductName(orderProduct.getProduct().getTranslations().get(0).getName());
                productDTO.setCategory(orderProduct.getCategory());
                productDTO.setQuantity(orderProduct.getQuantity());
                productDTO.setStatus(orderProduct.getStatus());
                return productDTO;
            }).collect(Collectors.toList());

            orderDTO.setProducts(productDTOs);
            return orderDTO;
        }).collect(Collectors.toList());
    }


}
