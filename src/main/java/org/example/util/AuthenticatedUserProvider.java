package org.example.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class AuthenticatedUserProvider {

    private final JwtService jwtService;

    private final UserService userService;

    public Long getUserId(HttpServletRequest request) {
        final String getHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        if(getHeader == null || !getHeader.startsWith("Bearer ")) {
            return null;
        }
        jwt = getHeader.substring(7);
        final var userName = jwtService.extractUsername(jwt);
        Optional<User> user = userService.findByUserName(userName);
        return user.get().getId();
    }
}
