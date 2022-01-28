package com.naprednoprogramiranje.aes.service.role;

import com.naprednoprogramiranje.aes.model.Role;
import com.naprednoprogramiranje.aes.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Captor
    ArgumentCaptor<Role> roleCaptor;
    @Mock
    RoleRepository roleRepository;
    @InjectMocks
    RoleServiceImpl roleService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findAll() {
        when(roleRepository.findAll())
                .thenReturn(List.of(
                    Role.builder()
                        .roleName("ROLE_ONE")
                        .build(),
                    Role.builder()
                        .roleName("ROLE_TWO")
                        .build(),
                    Role.builder()
                        .roleName("ROLE_THREE")
                        .build()
                ));
        List<Role> retrievedRoles = roleService.findAll();
        assertTrue(retrievedRoles.size() == 3, "Number of retrieved roles is valid");
    }

    @Test
    void findByRoleName() {
        when(roleRepository.findByRoleName(eq("ROLE_ONE")))
                .thenReturn(Role.builder()
                        .roleName("ROLE_ONE")
                        .build());
        when(roleRepository.findByRoleName(eq("ROLE_TWO")))
                .thenReturn(null);
        Role one = roleService.findByRoleName("ROLE_ONE");
        Role two = roleService.findByRoleName("ROLE_TWO");
        assertNotNull(one);
        assertNull(two);
    }

    @Test
    void findById() {
        when(roleRepository.findById(1))
                .thenReturn(Optional.ofNullable(Role.builder()
                        .roleName("ROLE_ONE")
                        .build()));
        when(roleRepository.findById(2))
                .thenReturn(Optional.empty());
        Optional<Role> one = roleService.findById(1);
        Optional<Role> two = roleService.findById(2);
        assertTrue(one.isPresent());
        assertFalse(two.isPresent());
    }

    @Test
    void save() {
        Role role1 = Role.builder()
                .roleName("ROLE_KACA")
                .build();
        roleService.save(role1);

        verify(roleRepository, times(1)).save(roleCaptor.capture());
        Role role2 = roleCaptor.getValue();
        assertNotNull(role1);
        assertNotNull(role2);
        assertEquals(role1.getRoleName(),role2.getRoleName());
    }

    @Test
    void checkIfRoleNameIsTaken() {
        Role role1 = Role.builder()
                .roleName("ROLE_ONE")
                .build();
        Role role2 = Role.builder()
                .roleName("ROLE_TWO")
                .build();
        Role existingRole = Role.builder()
                .roleName("ROLE_TWO")
                .build();
        Role nonExistingRole = Role.builder()
                .roleName("ROLE_THREE")
                .build();
        assertNotNull(role1);
        assertNotNull(role2);
        List<Role> roles = new ArrayList();
        roles.add(role1);
        roles.add(role2);
        assertFalse(roleService.checkIfRoleNameIsTaken(roles, nonExistingRole));
        assertTrue(roleService.checkIfRoleNameIsTaken(roles, existingRole));
    }
}