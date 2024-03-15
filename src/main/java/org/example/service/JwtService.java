package org.example.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService {

    String extractUsername(String jwt);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims,
                         UserDetails userDetails);

    String refreshToken(UserDetails userDetails);

    <T> T  extractClaim(String jwt, Function<Claims, T> claimsResolver);

    boolean isTokenValid(String jwt, UserDetails userDetails);

}
