package com.joaoyp.learningplatformbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaoyp.learningplatformbackend.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    @Value("${jwt.secret}")
    private String segredo;

    public String gerarToken(UserModel user){
        return JWT.create()
                .withIssuer("Online Learning Platform")
                .withSubject(user.getUsername())
                .withClaim("id", user.getId().toString())
                .withExpiresAt(LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("+01:00")))
                .sign(Algorithm.HMAC256(segredo));
    }

    public Map<String, String> validateToken(String token) throws JWTVerificationException {
        Map<String, String> tokenClaims = new HashMap<>();
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(segredo)).withIssuer("Online Learning Platform").build().verify(token);
            tokenClaims.put("id", jwt.getClaim("id").asString());
            tokenClaims.put("subject", jwt.getSubject());
            return tokenClaims;
        } catch (JWTVerificationException e){
            throw new JWTVerificationException("Error validating the JWT");
        }
    }
}
