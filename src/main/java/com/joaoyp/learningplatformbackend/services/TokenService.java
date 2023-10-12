package com.joaoyp.learningplatformbackend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.joaoyp.learningplatformbackend.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    public String validateToken(String token){
        try {
            return JWT.require(Algorithm.HMAC256(segredo)).withIssuer("Online Learning Platform").build().verify(token).getSubject();
        } catch (JWTVerificationException e){
            return e.toString();
        }
    }
}
