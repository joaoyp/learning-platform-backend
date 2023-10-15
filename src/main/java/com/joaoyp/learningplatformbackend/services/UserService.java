package com.joaoyp.learningplatformbackend.services;

import com.joaoyp.learningplatformbackend.dtos.UserDTO;
import com.joaoyp.learningplatformbackend.models.CourseModel;
import com.joaoyp.learningplatformbackend.models.UserModel;
import com.joaoyp.learningplatformbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public void saveUser(UserDTO userDTO) {
        String passwordEncrypted = new BCryptPasswordEncoder().encode(userDTO.password());
        var user = new UserModel(userDTO.username(), passwordEncrypted, userDTO.email());
        //BeanUtils.copyProperties(userDTO, user);
        userRepository.save(user);
    }

    public void updateUser(UserModel userModel) {
        userRepository.save(userModel);
    }

    // DELETE THIS AFTER JUST A TEST!!!
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }


    /*public UserDetails findByUsername(String subject) {
        return userRepository.findByUsername(subject);
    }*/
}
