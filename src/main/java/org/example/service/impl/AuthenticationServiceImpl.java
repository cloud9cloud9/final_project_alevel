package org.example.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.dto.auth.AuthenticationResponse;
import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegisterRequest;
import org.example.exception.LoginFailedException;
import org.example.exception.TokenVerifyException;
import org.example.exception.UserNotFoundException;
import org.example.exception.UserRegistrationException;
import org.example.model.Token;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.repository.UserRepository;
import org.example.service.AuthenticationService;
import org.example.service.JwtService;
import org.example.service.TokenService;
import org.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authManager;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        boolean isEmpty = userRepository.findByUserName(request.getUserName()).isEmpty();
        if(!isEmpty) {
            throw new UserRegistrationException();
        }
        var user = User.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserRole.ROLE_USER)
                .build();
        userService.create(user);
        var token = jwtService.generateToken(user);
        saveUserToken(user, token);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword())
        );
        var user = userService.findByUserName(request.getUserName())
                .orElseThrow(LoginFailedException::new);
        var token = jwtService.generateToken(user);
        saveUserToken(user, token);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }


    public void refresh(HttpServletRequest request,
                        HttpServletResponse response) throws IOException {

        var accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (accessToken == null || !accessToken.startsWith("Bearer ")) {
            throw new TokenVerifyException();
        }
        accessToken = accessToken.substring(7);
        final var userName = jwtService.extractUsername(accessToken);
        final var userDetails = userService.findByUserName(userName)
                .orElseThrow(UserNotFoundException::new);
        if (jwtService.isTokenValid(accessToken, userDetails)) {
            var refreshToken = jwtService.refreshToken(userDetails);
            revokeAllUserTokens(userDetails);
            saveUserToken(userDetails, refreshToken);
            var authResponse = AuthenticationResponse.builder()
                    .refreshToken(refreshToken)
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }

    public AuthenticationResponse refreshTokenUpdatedUser(Long userId) {
        var userById = userService.findById(userId);
        var token = jwtService.generateToken(userById);
        revokeAllUserTokens(userById);
        saveUserToken(userById, token);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .isExpired(false)
                .build();
        tokenService.create(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenService.findAllByUser_Id(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> token.setExpired(true));
            validUserTokens.forEach(tokenService::update);
        }
    }
}
