package com.naprednoprogramiranje.aes.service.user;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Enables user management options
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Service
public interface UserService {

    /**
     * Provides list of all available users
     *
     * @return List of all available users
     */
    List<User> findAll();

    /**
     * Searches for the user with provided ID
     *
     * @param id ID used for filtering
     *
     * @return Optional user with provided id. No value will be available if no user is found
     */
    Optional<User> findById(Long id);

    /**
     * Searches for the user with provided email
     *
     * @param email Email used for filtering
     *
     * @return User with provided email. Null will be returned if no user is found
     */
    User findByEmail(String email);

    /**
     * Searches for the user with provided username
     *
     * @param username Username used for filtering
     *
     * @return User with provided username. Null will be returned if no user is found
     */
    User findByUsername(String username);

    /**
     * Searches for the user with provided mobile phone number
     *
     * @param mobile Mobile phone number used for filtering
     *
     * @return User with provided mobile phone number. Null will be returned if no user is found
     */
    User findByMobile(String mobile);

    /**
     * Saves provided user in the database
     *
     * @param user User to be saved
     */
    void save(User user);

    /**
     * Deletes user with provided ID
     *
     * @param id ID of the user to be deleted
     */
    void deleteById(Long id);

    /**
     * Creates a user based on a provided user data in a command
     *
     * @param userDto User command with details of the user to be created
     *
     * @return User created based on the provided command
     */
    User createNewAccount(UserDto userDto);

    /**
     * Updates a user based on a provided user data in a command
     *
     * @param userDto ser command with details of the user to be updated
     *
     * @return User updated based on the provided command
     */
    User updateUser(UserDto userDto);

}
