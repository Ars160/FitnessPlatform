package org.example.fitnessplatform.dto;

import org.example.fitnessplatform.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDTO(User user);
    User toEntity(UserDto userDto);

    List<UserDto> toDTOList(List<User> users);
}
