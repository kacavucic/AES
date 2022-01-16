package com.naprednoprogramiranje.aes.web.service;

import com.naprednoprogramiranje.aes.service.user.UserService;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.naprednoprogramiranje.aes.web.mapper.Mapper.*;

@Service
@AllArgsConstructor
@Transactional
public class UserDtoService {

    private final UserService userService;

    public List<UserDto> findAll() {
        return convertToDtoList(userService.findAll());
    }

    public Optional<UserDto> findById(Long id) {
        return Optional.ofNullable(convertToDto(userService.findById(id).get()));
    }

    public UserDto findByEmail(String email) {
        return convertToDto(userService.findByEmail(email));
    }

    public UserDto findByUsername(String username) {
        return convertToDto(userService.findByUsername(username));

    }

}
