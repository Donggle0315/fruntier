package com.fruntier.fruntier;
import com.fruntier.fruntier.user.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.crypto.SecretKey;

@Service
public class JwtTokenService {
    private final SecretKey key;


    public JwtTokenService() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode("justshutupandgeneratemykeyfor256bytesorwhateverman");
            this.key = Keys.hmacShaKeyFor(keyBytes);
        }catch (Exception e){
            throw new RuntimeException("Error generating key for JWT",e);
        }
    }

    public String generateToken(User user) {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis + 86400000)) // 24-hour expiration
                .claim("id", user.getId())
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            // Additional validation can be done here (e.g., checking user ID, roles)

            return true; // Token is valid
        } catch (ExpiredJwtException | SignatureException | IllegalArgumentException e) {
            // Handle invalid token, expired token or token with invalid signature
            return false;
        }
    }
}
