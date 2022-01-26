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

    private final UserService userService;
    private final RoleService roleService;
    private boolean alreadySetup = false;

    public DataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

        createUserIfNotFound("admin@gmail.com", "Admin", "Admin", "admin", "Abc123$$$", "0", rolesAdmin);
        // createUserIfNotFound("vucic.kat@gmail.com", "Katarina", "Vucic", "kacavucic", "Abc123$$$", "+381693724133", rolesUser);
        createUserIfNotFound("kaca.kaca.kaca.kaca@gmail.com", "Ana", "Vucic", "anavucic", "Abc123$$$", "+381693724132", rolesUser);

        for (int i = 1; i < 5; i++)
            createUserIfNotFound("user" + i + "@gmail.com", "User" + i, "User" + i, "user" + i, "Abc123$$$" + i, i + "", rolesUser);
        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(final String roleName) {
        Role role = roleService.findByRoleName(roleName);
        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .build();
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
            user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .username(username)
                    .password(password)
                    .email(email)
                    .roles(userRoles)
                    .mobile(mobile)
                    .enabled(true)
                    .build();
            userService.save(user);
        }
    }
}
