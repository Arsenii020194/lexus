package ru.ssau.lexus.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public String getUsernameFromToken(String token) {
        return JWT.decode(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expiration = JWT.decode(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        return JWT
                .create()
                .withSubject(userDetails.getUsername())
                .withArrayClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

}
