package com.naprednoprogramiranje.aes.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorsTest {

    @Test
    public void testValidateFirstname() {
        Validators.validateFirstname("FIRSTNAME");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "A",
            "AA",
            "    AA",
            "AA    ",
            "    AA   "
    })
    public void testValidateFirstnameInvalid(String firstName) {
        assertThrows(RuntimeException.class, () -> Validators.validateFirstname(firstName));
    }

    @Test
    public void testValidateFirstnameNull() {
        assertThrows(NullPointerException.class, () -> Validators.validateFirstname(null));
    }

    @Test
    public void testValidateLastname() {
        Validators.validateLastName("LASTNAME");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "A",
            "    AA",
            "AA    ",
            "    AA   "
    })
    public void testValidateLastnameInvalid(String lastName) {
        assertThrows(RuntimeException.class, () -> Validators.validateLastName(lastName));
    }

    @Test
    public void testValidateLastnameNull() {
        assertThrows(NullPointerException.class, () -> Validators.validateLastName(null));
    }

    @Test
    public void testValidateUsername() {
        Validators.validateUsername("USERNAME");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "A",
            "    AA",
            "AA    ",
            "    AA   ",
            "USERNAME_USERNAME_USERNAME"
    })
    public void testValidateUsernameInvalid(String username) {
        assertThrows(RuntimeException.class, () -> Validators.validateUsername(username));
    }

    @Test
    public void testValidateUsernameInvalidNull() {
        assertThrows(NullPointerException.class, () -> Validators.validateUsername(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "kaca@vucic.com",
            "kaca.vucic@fon.gov",
            "kaca1@vucic.com"
    })

    public void testValidateEmail(String email) {
        Validators.validateEmail(email);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "a",
            "@",
            "a@",
            "@a",
            "a@s",
            "@s.s"
    })
    public void testValidateEmailInvalid(String email) {
        assertThrows(RuntimeException.class, () -> Validators.validateEmail(email));
    }

    @Test
    public void testValidateEmailInvalidNull() {
        assertThrows(NullPointerException.class, () -> Validators.validateEmail(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "AAAbbbccc@123",
            "Hello_world$123",
            "A!@#&()–a1",
            "@#$%^&+=Adfeg5",
            "A~$^+=<>a1",
            "0123456789$abcdefgAB",
            "123Aa$Aa"
    })
    public void testValidatePassword(String pass) {
        Validators.validatePassword(pass);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678",
        "abcdefgh",
        "ABCDEFGH",
        "abc123$$$",
        "ABC123$$$",
        "ABC$$$$$$",
        "javaREGEX123",
        "________",
        "--------"
    })
    public void testValidatePasswordInvalid(String pass) {
        assertThrows(RuntimeException.class, () -> Validators.validateEmail(pass));
    }

    @Test
    public void testValidatePasswordInvalidNull() {
        assertThrows(NullPointerException.class, () -> Validators.validateEmail(null));
    }

    @Test
    public void testValidateMobile() {
        Validators.validateMobile("0601234567");
    }

    @Test
    public void testValidateMobileNull() {
        assertThrows(NullPointerException.class, () -> Validators.validateMobile(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ROLE_ADMIN",
            "ROLE_SOMETHING",
            "ROLE_ABCDEFGHIJKLMNO"
    })
    public void testValidateRoleName(String roleName) {
        Validators.validateMobile(roleName);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ROLE",
            "ROLEABCD",
            "ROLE_ROLEROLEROLEROLE"
    })
    public void testValidateRoleNameInvalid(String roleName) {
        assertThrows(RuntimeException.class, () -> Validators.validateRoleName(roleName));
    }

    @Test
    public void testValidateRoleNameNull() {
        assertThrows(NullPointerException.class, () -> Validators.validateRoleName(null));
    }
}
