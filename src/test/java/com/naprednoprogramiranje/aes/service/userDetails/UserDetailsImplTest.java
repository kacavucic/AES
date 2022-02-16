package com.naprednoprogramiranje.aes.service.userDetails;

import com.naprednoprogramiranje.aes.model.Role;
import com.naprednoprogramiranje.aes.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserDetailsImplTest {

    @Test
    public void testGetUser() {
        List<Role> roles = new ArrayList();
        roles.add(Role.builder().roleName("ROLE_1").build());
        roles.add(Role.builder().roleName("ROLE_2").build());
        roles.add(Role.builder().roleName("ROLE_3").build());
        User user = User.builder()
                .firstName("FIRST_NAME")
                .lastName("LAST_NAME")
                .username("USERNAME")
                .password("Hello_world$123")
                .email("email@com.com")
                .mobile("0601234567")
                .roles(roles)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        assertNotNull(userDetails);
        assertEquals(userDetails.getUser(), user); // tests all subsequent props
        assertEquals(userDetails.getAuthorities().size(), roles.size()); // won't go through entire list, but only to assert number of role
    }
}
