package com.naprednoprogramiranje.aes.web.model;

import com.naprednoprogramiranje.aes.model.User;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private Integer id;
    private String roleName;
    private List<User> users;
}
