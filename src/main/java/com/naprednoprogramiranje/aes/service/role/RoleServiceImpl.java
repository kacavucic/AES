package com.naprednoprogramiranje.aes.service.role;

import com.naprednoprogramiranje.aes.model.Role;
import com.naprednoprogramiranje.aes.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public boolean checkIfRoleNameIsTaken(List<Role> allRoles, Role role) {
        boolean roleNameAlreadyExists = false;
        for (Role role1 : allRoles) {
            if (role.getRoleName().equals(role1.getRoleName())) {
                roleNameAlreadyExists = true;
                break;
            }
        }
        return roleNameAlreadyExists;
    }
}
