package com.joaoyp.learningplatformbackend.repositories;

import com.joaoyp.learningplatformbackend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
    Boolean existsByUsername(String username);
}
