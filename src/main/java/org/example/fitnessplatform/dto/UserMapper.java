package org.example.fitnessplatform.dto;

import org.example.fitnessplatform.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.name", target = "roleName")
    UserDto toDTO(User user);
    User toEntity(UserDto userDto);

    List<UserDto> toDTOList(List<User> users);
}
