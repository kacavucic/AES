package com.naprednoprogramiranje.aes.config;

import com.naprednoprogramiranje.aes.model.Role;
import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.service.role.RoleService;
import com.naprednoprogramiranje.aes.service.user.UserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final UserService userService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DataLoader(UserService userService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) return;

        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");
        Role roleUser = createRoleIfNotFound("ROLE_USER");

        List<Role> rolesAdmin = new ArrayList<>();
        rolesAdmin.add(roleAdmin);

        List<Role> rolesUser = new ArrayList<>();
        rolesUser.add(roleUser);

        createUserIfNotFound("admin@gmail.com", "Admin", "Admin", "admin", "admin", "0", rolesAdmin);
        createUserIfNotFound("vucic.kat@gmail.com", "Katarina", "Vucic", "kacavucic", "kaca", "+381693724133", rolesUser);
        createUserIfNotFound("kaca.kaca.kaca.kaca@gmail.com", "Ana", "Vucic", "anavucic", "ana", "+381693724132", rolesUser);

        for (int i = 1; i < 5; i++)
            createUserIfNotFound("user" + i + "@gmail.com", "User" + i, "User" + i, "user" + i, "user" + i, i + "", rolesUser);
        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(final String roleName) {
        Role role = roleService.findByRoleName(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleService.save(role);
        }
        return role;
    }

    @Transactional
    void createUserIfNotFound(final String email,
                              final String firstName,
                              final String lastName,
                              final String username,
                              final String password,
                              final String mobile,
                              final List<Role> userRoles) {
        User user = userService.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setEmail(email);
            user.setRoles(userRoles);
            user.setMobile(mobile);
            user.setEnabled(true);
            userService.save(user);
        }
    }
}
