package com.naprednoprogramiranje.aes.web.model;

import com.naprednoprogramiranje.aes.model.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class UserDto {

    private Long id;

    @NotBlank(message = "First name is required") // TODO Koristi NotNull umesto NotBlank
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Mobile is required")
    private String mobile;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private List<Role> roles;
    private boolean enabled;

}
