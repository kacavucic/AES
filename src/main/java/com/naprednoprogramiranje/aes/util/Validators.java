package com.naprednoprogramiranje.aes.util;

import java.util.regex.Pattern;

/**
 * Performs validation of various field types
 *
 * @author Katarina Vucic
 * @version 1.0
 */
public class Validators {

    /**
     * Validates provided first name
     *
     * First name must not be null and must have at least three characters
     *
     * @param firstName First name to be validated
     *
     * @throws NullPointerException If provided first name is null
     * @throws RuntimeException If provided first name has less than 3 characters
     */
    public static void validateFirstname(String firstName) throws NullPointerException, RuntimeException {
        if (firstName == null) {
            throw new NullPointerException("Firstname is required");
        }

        if (firstName.trim().length() < 3) {
            throw new RuntimeException("Firstname must contain at least 3 characters");
        }
    }

    /**
     * Validates provided last name
     *
     * Last name must not be null and must have at least three characters
     *
     * @param lastName Last name to be validated
     *
     * @throws NullPointerException If provided last name is null
     * @throws RuntimeException If provided last name has less than 3 characters
     */
    public static void validateLastName(String lastName) throws NullPointerException, RuntimeException {
        if (lastName == null) {
            throw new NullPointerException("Lastname is required");
        }
        if (lastName.trim().length() < 3) {
            throw new RuntimeException("Lastname must contain at least 3 characters");
        }
    }

    /**
     * Validates provided username
     *
     * Username must not be null and must have between 5 and 20 characters inclusively
     *
     * @param username First name to be validated
     *
     * @throws NullPointerException If provided username is null
     * @throws RuntimeException If provided username has less than 5 or more than 20 characters
     */
    public static void validateUsername(String username) throws NullPointerException, RuntimeException {
        if (username == null) {
            throw new NullPointerException("Username is required");
        }
        if (username.trim().length() < 5 || username.trim().length() > 20) {
            throw new RuntimeException("Username must contain at least 5 characters");
        }
    }

    /**
     * Validates provided email against RFC standard
     *
     * @param email Email to be validated
     *
     * @throws NullPointerException If provided email is null
     * @throws RuntimeException If provided email is not in the format defined by RFC standard
     */
    public static void validateEmail(String email) throws NullPointerException, RuntimeException {
        if (email == null) {
            throw new NullPointerException("Email is required");
        }

        if (!emailValid(email.trim())) {
            throw new RuntimeException("Email must be in valid format");
        }
    }

    /**
     * Validates provided password
     *
     * Password must contain at least:
     * <ul>
     *     <li>one digit</li>
     *     <li>one lowercase character</li>
     *     <li>one uppercase character</li>
     *     <li>one special character</li>
     *     <li>at least 8 characters</li>
     *     <li>at most 20 characters</li>
     * </ul>
     *
     * @param password Password to be validated
     * @throws NullPointerException If provided password is null
     * @throws RuntimeException If provided password in given format
     */
    public static void validatePassword(String password) throws NullPointerException, RuntimeException {
        if (password == null) {
            throw new NullPointerException("Password is required");
        }

        if (!passwordValid(password.trim())) {
            throw new RuntimeException("Password must contain at least one digit, one lowercase character, one uppercase character, one special character and must contain at least 8 and at most 20 characters");
        }
    }

    /**
     * Validates provided mobile phone number by checking if it is null or not
     *
     * @param mobile Mobile phone number to be validated
     * @throws NullPointerException If provided mobile phone is null
     */
    public static void validateMobile(String mobile) throws NullPointerException {
        if (mobile == null) {
            throw new NullPointerException("Mobile is required");
        }
    }

    /**
     * Validates provided role name
     *
     * Role must start with ROLE_ and must not have more than 20 characters
     *
     * @param roleName Role name to be validated
     *
     * @throws NullPointerException If provided role name is null
     * @throws RuntimeException If role name doesn't begin with "ROLE_" or if role name has more than 20 characters
     */
    public static void validateRoleName(String roleName) throws NullPointerException, RuntimeException {
        if (roleName == null) {
            throw new NullPointerException("Role name is required");
        }
        if (!roleName.trim().startsWith("ROLE_") || roleName.trim().length() > 20) {
            throw new RuntimeException("Role name must start with <ROLE_> and must not contain more than 20 characters");
        }
    }

    private static boolean passwordValid(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\\S+$).{8,20}$";
        return Pattern.compile(pattern)
                .matcher(password)
                .matches();
    }

    private static boolean emailValid(String email) {
        String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return Pattern.compile(pattern)
                .matcher(email)
                .matches();
    }


}
