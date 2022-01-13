package com.naprednoprogramiranje.aes.service.role;

import com.naprednoprogramiranje.aes.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {

    List<Role> findAll();

    Role findByRoleName(String roleName);

    Optional<Role> findById(Integer id);

    void save(Role role);

    boolean checkIfRoleNameIsTaken(List<Role> allRoles, Role role);
}
