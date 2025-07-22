package spring.practice.elmenus_lite.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.config.JwtConfig;
import spring.practice.elmenus_lite.model.Role;
import spring.practice.elmenus_lite.model.UserModel;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;


    public String generateAccessToken(UserModel user, Role role) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration(),role);
    }

    public String generateRefreshToken(UserModel user, Role role) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration(),role);
    }

    private String generateToken(UserModel user, long tokenExpiration, Role role) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("name", user.getFullName())
                .claim("email", user.getEmail())
                .claim("role", role.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration)) // 1 hour expiry
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (JwtException ex) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Integer getIdFromToken(String token) {
        return Integer.valueOf(getClaims(token).getSubject());
    }

    public Role getRoleFromToken(String token) {
        return Role.valueOf(getClaims(token).get("role", String.class));
    }
}

