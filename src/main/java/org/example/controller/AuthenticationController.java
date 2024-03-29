package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.ApiConstantPath;
import org.example.dto.auth.AuthenticationResponse;
import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegisterRequest;
import org.example.exception.InvalidTokenException;
import org.example.exception.LoginFailedException;
import org.example.exception.UserRegistrationException;
import org.example.service.AuthenticationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstantPath.API_V1_AUTH)
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping(value = ApiConstantPath.REGISTRATION)
    @Operation(summary = "Creates a user account",
            description = "Registers a unique user record in the system corresponding to the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User account created successfully",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "409", description = "User account with provided email-id already exists",
                    content = @Content(schema = @Schema(implementation = UserRegistrationException.class)))})
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) throws NoSuchAlgorithmException {
        log.info("Register request: {}", request);
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(value = ApiConstantPath.LOGIN)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successfull"),
            @ApiResponse(responseCode = "400", description = "Bad credentials provided. Failed to authenticate user",
                    content = @Content(schema = @Schema(implementation = LoginFailedException.class)))})
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody LoginRequest request) {
        log.info("Login request: {}", request);
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PutMapping(value = ApiConstantPath.REFRESH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Refreshes Access-Token for a user",
            description = "Provides a new Access-token against the user for which the non expired refresh-token is provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access-token refreshed"),
            @ApiResponse(responseCode = "403", description = "Refresh token has expired. Failed to refresh access token",
                    content = @Content(schema = @Schema(implementation = InvalidTokenException.class)))})
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        log.info("Refresh request: {}", request);
        authenticationService.refresh(request, response);
    }
}
