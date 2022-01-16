package com.naprednoprogramiranje.aes.service.user;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.repository.UserRepository;
import com.naprednoprogramiranje.aes.service.role.RoleService;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEnabled(userDto.isEnabled());
        user.setMobile(userDto.getMobile());
        user.setRoles(Collections.singletonList(roleService.findByRoleName("ROLE_USER")));
        return user;
    }

    @Override
    public User updateUser(UserDto userDto) {
        User user = findById(userDto.getId()).get();
        if (user == null) {
            throw new IllegalArgumentException("User not found for provided user id");
        }

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        //user.setEnabled(userDto.isEnabled());
        user.setMobile(userDto.getMobile());
        //user.setRoles(Collections.singletonList(roleService.findByRoleName("ROLE_USER")));
        return user;
    }

}
