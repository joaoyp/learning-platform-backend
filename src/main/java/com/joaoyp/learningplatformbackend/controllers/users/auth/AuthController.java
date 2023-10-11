package com.joaoyp.learningplatformbackend.controllers.users.auth;

import com.joaoyp.learningplatformbackend.dtos.UserDTO;
import com.joaoyp.learningplatformbackend.models.UserModel;
import com.joaoyp.learningplatformbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    UserService userService;

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
}
