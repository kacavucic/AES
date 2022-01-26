package com.naprednoprogramiranje.aes.util;

import java.util.regex.Pattern;

public class Validators {

    public static void validateFirstname(String firstName) {
        if (firstName == null) {
            throw new NullPointerException("Firstname is required");
        }

        if (firstName.trim().length() < 3) {
            throw new RuntimeException("Firstname must contain at least 3 characters");
        }
    }

    public static void validateLastName(String lastName) {
        if (lastName == null) {
            throw new NullPointerException("Lastname is required");
        }
        if (lastName.trim().length() < 3) {
            throw new RuntimeException("Lastname must contain at least 3 characters");
        }
    }

    public static void validateUsername(String username) {
        if (username == null) {
            throw new NullPointerException("Username is required");
        }
        if (username.trim().length() < 5 || username.trim().length() > 20) {
            throw new RuntimeException("Username must contain at least 5 characters");
        }
    }

    public static void validateEmail(String email) {
        if (email == null) {
            throw new NullPointerException("Email is required");
        }

        if (!emailValid(email)) {
            throw new RuntimeException("Email must be in valid format");
        }
    }

    public static void validatePassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password is required");
        }

        if (!passwordValid(password)) {
            throw new RuntimeException("Password must contain at least one digit, one lowercase character, one uppercase character, one special character and must contain at least 8 and at most 20 characters");
        }
    }

    public static void validateMobile(String mobile) {
        if (mobile == null) {
            throw new NullPointerException("Mobile is required");
        }
    }

    public static void validateRoleName(String roleName) {
        if (roleName == null) {
            throw new NullPointerException("Role name is required");
        }
        if (!roleName.startsWith("ROLE_") || roleName.length() > 20) {
            throw new RuntimeException("Role name must start with <ROLE_> and must not contain more than 20 characters");
        }
    }

    private static boolean passwordValid(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!^&+=])(?=\\S+$).{8,}$";
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
