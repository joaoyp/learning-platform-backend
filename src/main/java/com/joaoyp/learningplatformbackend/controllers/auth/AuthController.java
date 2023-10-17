package com.joaoyp.learningplatformbackend.controllers.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.joaoyp.learningplatformbackend.dtos.TokenDTO;
import com.joaoyp.learningplatformbackend.dtos.UserDTO;
import com.joaoyp.learningplatformbackend.models.UserModel;
import com.joaoyp.learningplatformbackend.services.TokenService;
import com.joaoyp.learningplatformbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid UserDTO userDTO){
        Map<String, String> response = new HashMap<>();
        try {
            if (userService.existsByUsername(userDTO.username())){
                response.put("message", "User already exists");
                return ResponseEntity.badRequest().body(response);
            }
            userService.saveUser(userDTO);
            response.put("message", "User successfully created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e){
            response.put("message", e.toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserDTO userDTO){
        Map<String, String> response = new HashMap<>();
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.gerarToken((UserModel) auth.getPrincipal());
            response.put("message", "Login Successfully");
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (AuthenticationException e){
            response.put("message", "Invalid Username or Password");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody TokenDTO tokenDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            Map<String, String> token = tokenService.validateToken(tokenDTO.token());
            response.put("message", "Valid Token");
            response.put("id", token.get("id"));
            response.put("subject", token.get("subject"));
            response.put("role", token.get("role"));
            return ResponseEntity.ok().body(response);
        } catch (JWTVerificationException e) {
            response.put("message", "Invalid Token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}