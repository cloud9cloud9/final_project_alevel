package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ExceptionResponseDto;
import org.example.dto.TokenSuccessResponseDto;
import org.example.dto.auth.AuthenticationResponse;
import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegisterRequest;
import org.example.exception.TokenVerifyException;
import org.example.service.AuthenticationService;
import org.example.service.JwtService;
import org.example.util.RefreshTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authService;

    private final JwtService jwtService;

    private final RefreshTokenProvider refreshTokenProvider;

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        log.info("Register request: {}", request);
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successfull"),
            @ApiResponse(responseCode = "401", description = "Bad credentials provided. Failed to authenticate user",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class)))})
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody LoginRequest request) {
        log.info("Login request: {}", request);
        return ResponseEntity.ok(authService.login(request));
    }

    @PutMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Refreshes Access-Token for a user", description = "Provides a new Access-token against the user for which the non expired refresh-token is provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access-token refreshed"),
            @ApiResponse(responseCode = "403", description = "Refresh token has expired. Failed to refresh access token",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseDto.class))) })
    public ResponseEntity<TokenSuccessResponseDto> refreshToken() {
        final var refreshToken = refreshTokenProvider.getRefreshToken().orElseThrow(TokenVerifyException::new);
        final var tokenResponse = jwtService.refreshToken(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }
}
