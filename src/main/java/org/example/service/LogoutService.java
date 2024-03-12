package org.example.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final JwtService jwtService;

    private final UserService userService;

    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        final String getHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;

        if(getHeader == null || !getHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = getHeader.substring(7);
        final var userName = jwtService.extractUsername(jwt);
        final var userDetails = userService.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if(jwtService.isTokenValid(jwt, userDetails)) {
            tokenService.deleteByToken(jwt);
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
}