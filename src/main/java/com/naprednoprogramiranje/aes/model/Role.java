package com.naprednoprogramiranje.aes.model;

import com.naprednoprogramiranje.aes.util.Validators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String roleName;

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
