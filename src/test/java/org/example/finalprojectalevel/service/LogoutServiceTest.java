package org.example.finalprojectalevel.service;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.service.JwtService;
import org.example.service.TokenService;
import org.example.service.UserService;
import org.example.service.impl.LogoutServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RequiredArgsConstructor
public class LogoutServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    @Autowired
    private LogoutServiceImpl logoutService;


    @BeforeEach
    public void setUp() {
        jwtService = Mockito.mock(JwtService.class);
        userService = Mockito.mock(UserService.class);
        tokenService = Mockito.mock(TokenService.class);
        logoutService = new LogoutServiceImpl(jwtService, userService, tokenService);
    }


    @Test
    public void testLogoutValidToken() {
        User user = User.builder()
                .userName("username")
                .email("email")
                .build();
        final var validToken = "Bearer validToken";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", validToken);
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication authentication = mock(Authentication.class);
        when(jwtService.extractUsername("validToken")).thenReturn("username");
        when(userService.findByUserName("username")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid("validToken", user)).thenReturn(true);

        logoutService.logout(request, response, authentication);

        // Verify that the response status code is set to 200 OK
        assertEquals(200, response.getStatus());
    }
}
