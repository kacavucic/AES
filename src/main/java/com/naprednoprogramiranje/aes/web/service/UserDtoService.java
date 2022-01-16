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

    public Optional<UserDto> findById(Long id) {
        return Optional.ofNullable(convertToDto(userService.findById(id).get()));
    }

}
