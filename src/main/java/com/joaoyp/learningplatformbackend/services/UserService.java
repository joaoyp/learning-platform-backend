package com.joaoyp.learningplatformbackend.services;

import com.joaoyp.learningplatformbackend.dtos.UserDTO;
import com.joaoyp.learningplatformbackend.models.UserModel;
import com.joaoyp.learningplatformbackend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserModel findUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    public void saveUser(UserDTO userDTO) {
        var user = new UserModel();
        BeanUtils.copyProperties(userDTO, user);
        userRepository.save(user);
    }
}