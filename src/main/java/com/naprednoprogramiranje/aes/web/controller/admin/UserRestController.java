package com.naprednoprogramiranje.aes.web.controller.admin;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.repository.UserRepository;
import com.naprednoprogramiranje.aes.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class UserRestController {

    private UserService userService;
    private UserRepository userRepository;

    @GetMapping("/adminPage/json-users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = userService.findAll();
        if (allUsers == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else if (allUsers.isEmpty()) return new ResponseEntity<>(allUsers, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public DataTablesOutput<User> list(@Valid DataTablesInput input) {
        return userRepository.findAll(input);
    }

    @DeleteMapping("/adminPage/json-users/delete/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userToDelete = userService.findById(id);

        if (userToDelete.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        userService.deleteById(id);
        return new ResponseEntity<>(userToDelete.get(), HttpStatus.NO_CONTENT);
    }
}