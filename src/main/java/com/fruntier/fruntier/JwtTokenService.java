package com.fruntier.fruntier;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

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

    public User validateTokenReturnUser(String token) throws Exception{
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Additional validation can be done here (e.g., checking user ID, roles)
            User loginUser = new User();
            loginUser.setUsername(claims.getSubject());
            loginUser.setId(claims.get("id",Long.class));


            return loginUser; // Token is valid
        } catch (ExpiredJwtException e) {
            throw new TokenValidationException("Token is expired", e);
        } catch (SignatureException e) {
            throw new TokenValidationException("Invalid token signature", e);
        } catch (IllegalArgumentException e) {
            throw new TokenValidationException("Token is invalid", e);
        }
    }
}
