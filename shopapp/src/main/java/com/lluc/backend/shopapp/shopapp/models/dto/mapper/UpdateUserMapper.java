package com.lluc.backend.shopapp.shopapp.models.dto.mapper;

import com.lluc.backend.shopapp.shopapp.models.dto.UpdateUserDTO;
import com.lluc.backend.shopapp.shopapp.models.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UpdateUserMapper {

    UpdateUserMapper INSTANCE = Mappers.getMapper(UpdateUserMapper.class);

    // Mapear de UpdateUserDTO a User
    User toEntity(UpdateUserDTO updateUserDTO);

    // Mapear de User a UpdateUserDTO
    UpdateUserDTO toDTO(User user);
}