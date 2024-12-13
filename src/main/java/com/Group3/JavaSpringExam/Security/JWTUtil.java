package com.Group3.JavaSpringExam.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.secretKeyFor;

@Data
@Component
public class JWTUtil {
    private SecretKey key = secretKeyFor(SignatureAlgorithm.HS256);

    private final String SECRET_KEY = Encoders.BASE64.encode(key.getEncoded());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public boolean isTokenValid(String token, String username) {
        final String extractedName = extractUsername(token);
        return username.equals(extractedName) && !isTokenExpired(token);
    }
}
