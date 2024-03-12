package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.model.Token;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${token.signing.key}")
    private String SECRET_KEY;

    @Value("${token.signing.expiration}")
    private long jwtExpiration;

    @Value("${token.signing..refresh-token.expiration}")
    private long refreshExpiration;

    private final TokenService tokenService;

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("username", customUserDetails.getUsername());
            claims.put("id", customUserDetails.getId());
            claims.put("role", customUserDetails.getRole().name());
        }
        return generateToken(claims, userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        return buildToken(
                extraClaims,
                userDetails,
                jwtExpiration
        );
    }

    public String refreshToken(UserDetails userDetails) {
        return buildToken(
                new HashMap<>(),
                userDetails,
                refreshExpiration
        );
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build();
        return parser.parseClaimsJws(jwt).getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        final Token token = tokenService.findByToken(jwt);
        return (username.equals(userDetails.getUsername())) && !token.isExpired();
    }


    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }
}