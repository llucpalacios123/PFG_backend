package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.UserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapear de User a UserDTO
    @Mapping(target = "token", ignore = true) // Ignorar el campo "token" porque no está en User
    @Mapping(target = "companyId", source = "empresa.id") // Mapear el ID de la empresa asociada
    UserDTO toDTO(User user);

    // Mapear de UserDTO a User
    @Mapping(target = "empresa", ignore = true) // Ignorar la empresa porque no está en el DTO
    User toEntity(UserDTO userDTO);
}