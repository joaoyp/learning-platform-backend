package com.joaoyp.learningplatformbackend.services;

import com.joaoyp.learningplatformbackend.dtos.CourseDTO;
import com.joaoyp.learningplatformbackend.models.CourseModel;
import com.joaoyp.learningplatformbackend.repositories.CourseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Optional<CourseModel> findById(UUID id) {
        return courseRepository.findById(id);
    }

    public boolean existsById(UUID id) {
        return courseRepository.existsById(id);
    }

    public List<CourseModel> getAllCourses() {
        return courseRepository.findAll();
    }

    public void saveCourseDTO(CourseDTO courseDTO) {
        var course = new CourseModel();
        BeanUtils.copyProperties(courseDTO, course);
        courseRepository.save(course);
    }

    public void updateCourse(CourseModel courseModel) {
        courseRepository.save(courseModel);
    }

    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }

    public List<CourseModel> findCoursesEnrolledByUserId(UUID userId) {
        return courseRepository.findCoursesEnrolledByUserId(userId);
    }
}
