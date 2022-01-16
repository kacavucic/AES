package com.naprednoprogramiranje.aes.service.user;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByMobile(String mobile);

    void save(User user);

    void deleteById(Long id);

    User createNewAccount(UserDto userDto);

}
