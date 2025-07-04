package spring.practice.elmenus_lite.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
    public class JwtService {

        // TODO MOVE TO .env
        // In production, load this from a secure location!
        private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        public String generateToken(String username) {
            long now = System.currentTimeMillis();
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + 1000 * 60 * 60)) // 1 hour expiry
                    .signWith(key)
                    .compact();
        }
    }

