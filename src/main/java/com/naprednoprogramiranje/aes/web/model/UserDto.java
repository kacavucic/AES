package com.naprednoprogramiranje.aes.web.model;

import com.naprednoprogramiranje.aes.model.Role;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class UserDto {

    private Long id;

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "Mobile is required")
    private String mobile;

    @NotNull(message = "Username is required")
    private String username;

    @NotNull(message = "Password is required")
    private String password;

    private List<Role> roles;
    private boolean enabled;

}
