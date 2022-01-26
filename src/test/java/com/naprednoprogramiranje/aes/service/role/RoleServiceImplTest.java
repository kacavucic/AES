package com.naprednoprogramiranje.aes.service.role;

import com.naprednoprogramiranje.aes.model.Role;
import com.naprednoprogramiranje.aes.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoleServiceImplTest {

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
    }

    @Test
    void findByRoleName() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
        Role role1 = Role.builder()
                .roleName("ROLE_KACA")
                .build();
        roleService.save(role1);

        Role role2 = roleService.findByRoleName("ROLE_KACA");
        assertNotNull(role1);
        assertEquals(role1.getRoleName(),role2.getRoleName());
    }

    @Test
    void checkIfRoleNameIsTaken() {
    }
}