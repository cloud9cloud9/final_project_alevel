package org.example.finalprojectalevel.controller;


import org.example.controller.AuthenticationController;
import org.example.dto.auth.AuthenticationResponse;
import org.example.dto.auth.RegisterRequest;
import org.example.service.AuthenticationService;
import org.example.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    public void setUp() {
        authenticationService = Mockito.mock(AuthenticationServiceImpl.class);
        authenticationController = new AuthenticationController(authenticationService);
    }

    @Test
    public void testRegisterSuccess() throws NoSuchAlgorithmException {
        RegisterRequest request = new RegisterRequest("JohnDoe", "johndoe@example.com", "password123");
        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
                .accessToken("token")
                .build();

        when(authenticationService.register(request)).thenReturn(expectedResponse);

        ResponseEntity<AuthenticationResponse> response = authenticationController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        verify(authenticationService).register(request);
    }
}
