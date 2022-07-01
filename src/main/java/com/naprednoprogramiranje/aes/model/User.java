package com.naprednoprogramiranje.aes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naprednoprogramiranje.aes.util.Validators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.security.SecureRandom;
import java.util.List;

/**
 * User model
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    /**
     * Database generated ID of the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's firstname
     */
    @Column(nullable = false)
    private String firstName;

    /**
     * User's lastname
     */
    @Column(nullable = false)
    private String lastName;

    /**
     * User's email address
     */
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * User's mobile phone
     */
    @Column(unique = true, nullable = false)
    private String mobile;

    /**
     * User's username
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * User's password
     */
    @JsonIgnore
    @Column(length = 60, nullable = false)
    private String password;

    /**
     * Indicates whether user's account is enabled or disabled
     */
    @Column(columnDefinition = "tinyint", nullable = false)
    private boolean enabled;

    /**
     * List of roles owned by user
     */
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles;

    /**
     * Sets attribute enabled to true or false
     *
     * @param enabled Indicates whether user's account is enabled or disabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static UserBuilder builder() {
        return new CustomBuilder();
    }

    private static class CustomBuilder extends UserBuilder {

        public User build() {
            Validators.validateFirstname(super.firstName);
            Validators.validateLastName(super.lastName);
            Validators.validateUsername(super.username);
            Validators.validateEmail(super.email);
            Validators.validatePassword(super.password);
            Validators.validateMobile(super.mobile);

            super.password = new BCryptPasswordEncoder(12,new SecureRandom()).encode(super.password);

            return super.build();
        }
    }
}
