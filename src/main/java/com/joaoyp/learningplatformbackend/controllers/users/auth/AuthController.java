package com.joaoyp.learningplatformbackend.controllers.users.auth;

import com.fasterxml.jackson.core.JsonPointer;
import com.joaoyp.learningplatformbackend.dtos.LoginResponseDTO;
import com.joaoyp.learningplatformbackend.dtos.UserDTO;
import com.joaoyp.learningplatformbackend.models.UserModel;
import com.joaoyp.learningplatformbackend.services.TokenService;
import com.joaoyp.learningplatformbackend.services.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
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
        if (userService.existsByUsername(userDTO.username())){
            response.put("message", "User already exists");
            return ResponseEntity.badRequest().body(response);
        }
        userService.saveUser(userDTO);
        response.put("message", "User successfully created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody UserDTO userDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(userDTO.username(), userDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.gerarToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}