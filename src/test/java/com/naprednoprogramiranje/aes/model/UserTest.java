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
            // first name ok
            "Kat,Vucic,kacavucic,AAAbbbccc@123,vucic.kat@gmail.com,0601234567",
            // last name ok
            "Katarina,Vuc,kacavucic,AAAbbbccc@123,vucic.kat@gmail.com,0601234567",
            // username ok
            "Katarina,Vucic,kacav,AAAbbbccc@123,vucic.kat@gmail.com,0601234567",
            "Katarina,Vucic,kacavucickacavucicka,AAAbbbccc@123,vucic.kat@gmail.com,0601234567",
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
        assertNotNull(user.getPassword()); // because password is encrypted after validation and before setting
        assertEquals(user.getEmail(), email);
        assertEquals(user.getMobile(), mobile);
    }

    @ParameterizedTest
    @CsvSource({
            // firstname less than 3 characters
            "Ka,Vucic,kacavucic,Abc12345!,vucic.kat@gmail.com,0601234567",
            // lastname less than 3 characters
            "Katarina,Vu,kacavucic,Abc12345!,vucic.kat@gmail.com,0601234567",
            // username
            "Katarina,Vucic,kaca,Abc12345!,vucic.kat@gmail.com,0601234567", // less than 5 characters
            "Katarina,Vucic,kacavucickacavucickac,Abc12345!,vucic.kat@gmail.com,0601234567", // more than 20 characters
            // password
            "Katarina,Vucic,kacavucic,12345678,vucic.kat@gmail.com,0601234567", // only digits
            "Katarina,Vucic,kacavucic,abcdefgh,vucic.kat@gmail.com,0601234567", // only lowercase
            "Katarina,Vucic,kacavucic,ABCDEFGH,vucic.kat@gmail.com,0601234567", // only uppercase
            "Katarina,Vucic,kacavucic,!!!!!!!!,vucic.kat@gmail.com,0601234567", // only special characters
            "Katarina,Vucic,kacavucic,abcdefg,vucic.kat@gmail.com,0601234567", // less than 8 characters
            "Katarina,Vucic,kacavucic,abcdefghijklmnopqrstu,vucic.kat@gmail.com,0601234567", // more than 20 characters
            "Katarina,Vucic,kacavucic,AAAbbbccc@,vucic.kat@gmail.com,0601234567", // no digits
            "Katarina,Vucic,kacavucic,AAABBBCCC@123,vucic.kat@gmail.com,0601234567", // no lowercase
            "Katarina,Vucic,kacavucic,aaabbbccc@123,vucic.kat@gmail.com,0601234567", // no uppercase
            "Katarina,Vucic,kacavucic,AAAbbbccc123,vucic.kat@gmail.com,0601234567", // no special character
            // email
            "Katarina,Vucic,kacavucic,Abc12345!,vucic.kat_gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,Abc12345!,vucic.kat@gmailcom,0601234567",
            "Katarina,Vucic,kacavucic,Abc12345!,vucic.kat_gmail.Com,0601234567",
            "Katarina,Vucic,kacavucic,Abc12345!,vucic.ka t_gmail.com,0601234567",
            "Katarina,Vucic,kacavucic,Abc12345!,vucic.kat@@gmail.com,0601234567",
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