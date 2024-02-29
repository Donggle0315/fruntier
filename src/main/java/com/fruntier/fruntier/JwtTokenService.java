package com.fruntier.fruntier;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
import com.fruntier.fruntier.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import javax.crypto.SecretKey;

@Service
public class JwtTokenService {
    @Autowired
    private UserRepository userRepository;
    private final SecretKey key;


    public JwtTokenService() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(generateRandomString(64));
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

    public User validateTokenReturnUser(String token) throws TokenValidationException{
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Additional validation can be done here (e.g., checking user ID, roles)
            Optional<User> userOptional = userRepository.findById(claims.get("id", Long.class));
            if(userOptional.isEmpty()){
                throw new TokenValidationException("User not found");
            }
            return userOptional.get(); // Token is valid
        } catch (ExpiredJwtException e) {
            throw new TokenValidationException("Token is expired");
        } catch (SignatureException e) {
            throw new TokenValidationException("Invalid token signature");
        } catch (IllegalArgumentException e) {
            throw new TokenValidationException("Token is invalid");
        }
    }

    private String generateRandomString(int targetStringLength){
        Random random = new Random();

        return random.ints(97, 122 + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
