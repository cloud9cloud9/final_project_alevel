package org.example.finalprojectalevel.controller;


import org.example.controller.UserController;
import org.example.dto.UserDto;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.service.AuthenticationService;
import org.example.service.UserService;
import org.example.util.AuthenticatedUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticatedUserProvider authenticatedUserProvider;

    @Mock
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        authenticatedUserProvider = mock(AuthenticatedUserProvider.class);
        authenticationService = mock(AuthenticationService.class);
        userController = new UserController(userService, userMapper, authenticatedUserProvider, authenticationService);
    }


    @Test
    public void testGetUserById_ValidId() {
        Long userId = 1L;
        User user = User.builder().userName("user1").build();
        when(userService.findById(userId)).thenReturn(user);

        when(userMapper.toUserDto(user)).thenReturn(new UserDto(1L, "user1", "password1", UserRole.ROLE_USER));

        UserDto result = userController.getUserById(userId);

        assertNotNull(result);
        assertEquals(1l, result.id());
        assertEquals(user.getUsername(), result.userName());
    }
}
