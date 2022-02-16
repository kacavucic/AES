package com.naprednoprogramiranje.aes.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    Role role;

    @ParameterizedTest
    @ValueSource(strings = {
            "ROLE_ADMIN",
            "ROLE_SOMETHING",
            "ROLE_ABCDEFGHIJKLMNO"
    })
    void testBuilderValid(String roleName) {
        role = Role.builder()
                .roleName(roleName)
                .build();

        assertNotNull(role);
        assertEquals(role.getRoleName(), roleName);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            // doesn't start with "ROLE_"
            "ABCD",
            "ADMIN_ROLE",
            "_ROLE_ADMIN",

            // more than 20 characters
            "ROLE_SOMETHING_SOMETHING",
            "ROLE_ABCDEFGHIJKLMNOP"
    })
    void testBuilderInvalid(String roleName) {
        assertThrows(RuntimeException.class, () -> Role.builder()
                .roleName(roleName)
                .build());
    }

    @ParameterizedTest
    @NullSource
    void testBuilderInvalidNull(String roleName) {
        assertThrows(NullPointerException.class, () -> Role.builder()
                .roleName(roleName)
                .build());
    }
}