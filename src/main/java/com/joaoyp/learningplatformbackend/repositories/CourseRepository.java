package com.joaoyp.learningplatformbackend.repositories;

import com.joaoyp.learningplatformbackend.models.CourseModel;
import com.joaoyp.learningplatformbackend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    boolean existsByName(String name);
}
