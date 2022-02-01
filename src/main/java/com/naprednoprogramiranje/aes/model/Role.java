package com.naprednoprogramiranje.aes.model;

import com.naprednoprogramiranje.aes.util.Validators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Role model
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Database generated ID of the role
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Name of the role
     */
    @Column(nullable = false, unique = true)
    private String roleName;

    /**
     * List of users having the given role
     */
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<User> users;

    public static RoleBuilder builder() {
        return new CustomBuilder();
    }

    private static class CustomBuilder extends RoleBuilder {

        public Role build() {
            Validators.validateRoleName(super.roleName);
            return super.build();
        }
    }

}
