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
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobile;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(length = 128, nullable = false)
    @Size(min = 8, max = 128)
    private String password;

    @Column(columnDefinition = "tinyint", nullable = false)
    private boolean enabled;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles;

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

            super.password = new BCryptPasswordEncoder().encode(super.password);

            return super.build();
        }
    }
}
