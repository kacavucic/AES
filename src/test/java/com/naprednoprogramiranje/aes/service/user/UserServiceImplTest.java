package com.naprednoprogramiranje.aes.service.user;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.repository.UserRepository;
import com.naprednoprogramiranje.aes.service.role.RoleService;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Captor
    ArgumentCaptor<User> userCaptor;
    @Captor
    ArgumentCaptor<Long> userIdCaptor;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @InjectMocks
    private UserServiceImpl userService;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testFindAll() {
        when(userRepository.findAll())
                .thenReturn(List.of(
                        User.builder()
                                .firstName("FIRST_NAME1")
                                .lastName("LAST_NAME1")
                                .username("USERNAME1")
                                .password("Hello_world$123")
                                .email("email1@com.com")
                                .mobile("0601234567")
                                .build(),
                        User.builder()
                                .firstName("FIRST_NAME2")
                                .lastName("LAST_NAME2")
                                .username("USERNAME2")
                                .password("Hello_world$123")
                                .email("email2@com.com")
                                .mobile("0601234567")
                                .build(),
                        User.builder()
                                .firstName("FIRST_NAME3")
                                .lastName("LAST_NAME3")
                                .username("USERNAME3")
                                .password("Hello_world$123")
                                .email("email3@com.com")
                                .mobile("0601234567")
                                .build()
                ));
        List<User> retrievedUsers = userService.findAll();
        assertEquals(retrievedUsers.size(), 3, "Number of retrieved users corresponds to the number of existing users");
    }

    @Test
    public void testFindById() {
        when(userRepository.findById(1l))
                .thenReturn(Optional.ofNullable(User.builder()
                        .firstName("FIRST_NAME")
                        .lastName("LAST_NAME")
                        .username("USERNAME")
                        .password("Hello_world$123")
                        .email("email@com.com")
                        .mobile("0601234567")
                        .build()));
        when(userRepository.findById(2l))
                .thenReturn(Optional.empty());
        Optional<User> one = userService.findById(1l);
        Optional<User> two = userService.findById(2l);
        assertTrue(one.isPresent());
        assertFalse(two.isPresent());
    }

    @Test
    public void testFindByEmail() {
        when(userRepository.findByEmail(eq("email@com.com")))
                .thenReturn(User.builder()
                        .firstName("FIRST_NAME")
                        .lastName("LAST_NAME")
                        .username("USERNAME")
                        .password("Hello_world$123")
                        .email("email@com.com")
                        .mobile("0601234567")
                        .build());
        when(userRepository.findByEmail(eq("email2@com.com")))
                .thenReturn(null);
        User one = userService.findByEmail("email@com.com");
        User two = userService.findByEmail("email2@com.com");
        assertNotNull(one);
        assertNull(two);
    }

    @Test
    public void testFindByUsername() {
        when(userRepository.findByUsername(eq("USERNAME")))
                .thenReturn(User.builder()
                        .firstName("FIRST_NAME")
                        .lastName("LAST_NAME")
                        .username("USERNAME")
                        .password("Hello_world$123")
                        .email("email@com.com")
                        .mobile("0601234567")
                        .build());
        when(userRepository.findByUsername(eq("USERNAME2")))
                .thenReturn(null);
        User one = userService.findByUsername("USERNAME");
        User two = userService.findByUsername("USERNAME2");
        assertNotNull(one);
        assertNull(two);
    }

    @Test
    public void testFindByMobile() {
        when(userRepository.findByMobile(eq("0601234567")))
                .thenReturn(User.builder()
                        .firstName("FIRST_NAME")
                        .lastName("LAST_NAME")
                        .username("USERNAME")
                        .password("Hello_world$123")
                        .email("email@com.com")
                        .mobile("0601234567")
                        .build());
        when(userRepository.findByMobile(eq("0601234568")))
                .thenReturn(null);
        User one = userService.findByMobile("0601234567");
        User two = userService.findByMobile("0601234568");
        assertNotNull(one);
        assertNull(two);
    }

    @Test
    public void testSave() {
        User user1 = User.builder()
                .firstName("FIRST_NAME")
                .lastName("LAST_NAME")
                .username("USERNAME")
                .password("Hello_world$123")
                .email("email@com.com")
                .mobile("0601234567")
                .build();
        userService.save(user1);

        verify(userRepository, times(1)).save(userCaptor.capture());
        User user2 = userCaptor.getValue();
        assertNotNull(user1);
        assertNotNull(user2);
        assertEquals(user1.getUsername(), user2.getUsername());
    }

    @Test
    public void testDeleteById() {
        userService.deleteById(1l);
        verify(userRepository, times(1)).deleteById(userIdCaptor.capture());
        Long id = userIdCaptor.getValue();
        assertEquals(id, 1l);
    }

    @Test
    public void testCreateNewAccount() {
        UserDto userDto = new UserDto(1l, "FIRST", "LAST", "email@com.com", "0601234567", "USERNAME", "Pass123!", null, true);
        User user = userService.createNewAccount(userDto);
        assertNotNull(user);
        assertNull(user.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getMobile(), userDto.getMobile());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getPassword().length() > userDto.getUsername().length());
        assertEquals(user.isEnabled(), userDto.isEnabled());
    }

    @Test
    public void testUpdateUser() {
        when(userService.findById(1l))
                .thenReturn(Optional.ofNullable(User.builder()
                        .firstName("FIRST_NAME")
                        .lastName("LAST_NAME")
                        .username("USERNAME")
                        .password("Hello_world$123")
                        .email("email@com.com")
                        .mobile("0601234567")
                        .build()));
        Optional<User> tmpUserOpt = userService.findById(1l);
        assertTrue(tmpUserOpt.isPresent());
        User tmpUser = tmpUserOpt.get();
        UserDto userDto = new UserDto(1l, "FIRST", "LAST", "email@com.com", "0601234567", "USERNAME", "Pass123!", null, true);
        User user = userService.updateUser(userDto);
        assertNotNull(user);
        assertNotEquals(user, tmpUser);
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getMobile(), userDto.getMobile());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.getPassword().length() > userDto.getUsername().length());
    }
}
