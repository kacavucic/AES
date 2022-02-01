package com.naprednoprogramiranje.aes.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @Test
    void setEnabled() {
        assertFalse(user.isEnabled());
        user.setEnabled(true);
        assertTrue(user.isEnabled());
    }

    @ParameterizedTest
    @CsvSource({
            // passwords ok
            "Katarina,Vucic,kacavucic,AAAbbbccc@123,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,Hello_world$123,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,A!@#&()–a1,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,@#$%^&+=Adfeg5,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,A~$^+=<>a1,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,0123456789$abcdefgAB,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,123Aa$Aa,vucic.kat@gmail.com,0601234567"
    })
    void testBuilderValid(String firstName, String lastName, String username, String password, String email,
                          String mobile) {
        user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .email(email)
                .mobile(mobile)
                .build();
        assertNotNull(user);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getLastName(), lastName);
        assertEquals(user.getUsername(), username);
        assertNotNull(user.getPassword());
        assertEquals(user.getEmail(), email);
        assertEquals(user.getMobile(), mobile);
    }

    @ParameterizedTest
    @CsvSource({
            // firstname
            "Ka,Vucic,kacavucic,Abc12345!,vucic.kat@gmail.com,0601234567",
            // lastname
            "Katarina,Vu,kacavucic,Abc12345!,vucic.kat@gmail.com,0601234567",
            // username
            "Katarina,Vucic,kaca,Abc12345!,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucickacavucickac,Abc12345!,vucic.kat@gmail.com,0601234567",
            // password
            "Katarina,Vucic,kacavucic,12345678,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,abcdefgh,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,ABCDEFGH,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,abc123$$$,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,ABC123$$$,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,ABC$$$$$$,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,javaREGEX123,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,________,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,--------,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,' ',vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,'',vucic.kat@gmail.com,0601234567",
            // email
            "Katarina,Vucic,kacavucic,Abc12345!,vucic.kat_gmail.com,0601234567"
    })
    void testBuilderInvalid(String firstName, String lastName, String username, String password, String email, String mobile) {
        assertThrows(RuntimeException.class, () -> User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .email(email)
                .mobile(mobile)
                .build());
    }

    @ParameterizedTest
    @CsvSource({
            ",Vucic,kacavucic,Abc12345!,vucic.kat@gmail.com,0601234567",
            "Katarina,,kacavucic,Abc12345!,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,,Abc12345!,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,Abc12345!,,0601234567",
            "Katarina,Vucic,kacavucic,Abc12345!,vucic.kat@gmail.com,"
    })
    void testBuilderInvalidNull(String firstName, String lastName, String username, String password, String email, String mobile) {
        assertThrows(NullPointerException.class, () -> User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .email(email)
                .mobile(mobile)
                .build());
    }
}