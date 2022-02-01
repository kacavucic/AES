package com.naprednoprogramiranje.aes.service.user;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.repository.UserRepository;
import com.naprednoprogramiranje.aes.service.role.RoleService;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByMobile(String mobile) {
        return userRepository.findByMobile(mobile);
    }


    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User createNewAccount(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .enabled(userDto.isEnabled())
                .mobile(userDto.getMobile())
                .roles(Collections.singletonList(roleService.findByRoleName("ROLE_USER")))
                .build();
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = findById(userDto.getId()).get();
        if (user == null) {
            throw new IllegalArgumentException("User not found for provided user id");
        }
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .mobile(userDto.getMobile())
                .build();
    }

}
