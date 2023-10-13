package com.joaoyp.learningplatformbackend.controllers.courses;

import com.joaoyp.learningplatformbackend.dtos.CourseDTO;
import com.joaoyp.learningplatformbackend.models.CourseModel;
import com.joaoyp.learningplatformbackend.services.CourseService;
import com.joaoyp.learningplatformbackend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<CourseModel>> getAllCourses(){
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    @GetMapping("/{course_id}")
    public ResponseEntity<Optional<CourseModel>> getCourse(@PathVariable(value="course_id") UUID course_id){
        return ResponseEntity.ok().body(courseService.findById(course_id));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createCourse(@RequestBody CourseDTO courseDTO){
        Map<String, String> response = new HashMap<>();
        try {
            if (courseService.existsByName(courseDTO.name())){
                response.put("message", "Course already exists");
                return ResponseEntity.badRequest().body(response);
            }
            courseService.saveCourse(courseDTO);
            response.put("message", "Course successfully created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e){
            response.put("message", e.toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /*@PostMapping("/enroll/{course_id}")
    public ResponseEntity<String> enrollUser(
            @PathVariable(value = "course_id") UUID course_id,
            @RequestBody TokenDTO token
    ) {
        Optional<CourseModel> course = courseService.findById(enrollCourseDTO.course_id());
        Optional<UserModel> user = userService.findById(enrollCourseDTO.user_id());

        if (course != null && user != null) {
            course.getUsersEnrolled().add(user);
            courseService.saveCourse(course);

            return ResponseEntity.ok("User enrolled in the course.");
        } else {
            return ResponseEntity.badRequest("Invalid course or user.");
        }
    }*/
}
