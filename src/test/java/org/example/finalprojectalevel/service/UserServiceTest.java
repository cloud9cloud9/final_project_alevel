package org.example.finalprojectalevel.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserUpdateRequestDto;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RequiredArgsConstructor
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }


    @Test
    public void testCreateNewUserWithUniqueUsername() {
        User buildedUser = createMockUser();

        when(userRepository.existsByUserName("uniqueUsername")).thenReturn(false);

        userService.create(buildedUser);

        verify(userRepository, times(1)).save(buildedUser);
    }


    @Test
    public void testUpdateUser() {

        UserUpdateRequestDto updatedUser = new UserUpdateRequestDto("newUsername", "newemail@example.com");

        User existingUser = createMockUser();
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);
        existingUser.setUserName(updatedUser.userName());
        existingUser.setEmail(updatedUser.email());

        userService.update(id, updatedUser);

        assertEquals("newUsername", existingUser.getUsername());
        assertEquals("newemail@example.com", existingUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        Mockito.doNothing().when(userRepository).deleteById(userId);
        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    private User createMockUser() {
        return User.builder()
                .userName("uniqueUsername")
                .email("email")
                .password("password")
                .build();
    }
}
