package com.naprednoprogramiranje.aes.web.mapper;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {

    private final static ModelMapper MAPPER = new ModelMapper();

    public static List<UserDto> convertToDtoList(List<User> users) {
        return users.stream()
                .map(u -> MAPPER.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public static UserDto convertToDto(User user) {
        return MAPPER.map(user, UserDto.class);
    }

}
