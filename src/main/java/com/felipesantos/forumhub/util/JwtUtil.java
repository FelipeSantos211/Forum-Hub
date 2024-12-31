package com.felipesantos.forumhub.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${secret.key}")
    private String SECRET_KEY;

    @Value("${issuer}")
    private String ISSUER;

    @Value("${expiration.time}")
    private long EXPIRATION_TIME;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY);
            JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}