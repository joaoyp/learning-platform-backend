package com.joaoyp.learningplatformbackend.controllers.courses;

import com.joaoyp.learningplatformbackend.dtos.CourseDTO;
import com.joaoyp.learningplatformbackend.dtos.TokenDTO;
import com.joaoyp.learningplatformbackend.models.CourseModel;
import com.joaoyp.learningplatformbackend.models.UserModel;
import com.joaoyp.learningplatformbackend.services.CourseService;
import com.joaoyp.learningplatformbackend.services.TokenService;
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

    @Autowired
    TokenService tokenService;

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
            courseService.saveCourseDTO(courseDTO);
            response.put("message", "Course successfully created");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e){
            response.put("message", e.toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/enroll/{course_id}")
    public ResponseEntity<Map<String, List<String>>> enrollUser(
            @PathVariable(value = "course_id") UUID course_id,
            @RequestBody TokenDTO tokenDTO
    ) {
        //TODO UserCourseModel (from the Many to Many relationship)
        return ResponseEntity.ok().build();
    }
}

        /*UUID user_id = UUID.fromString(tokenService.validateToken(tokenDTO.token()).get("id"));

        Optional<UserModel> user = userService.findById(user_id);
        Optional<CourseModel> course = courseService.findById(course_id);

        if (user.isPresent() && course.isPresent()) {
            if (course.get().getUsers_enrolled().contains(user.get())) {
                return ResponseEntity.badRequest().body("User is already enrolled in the course.");
            }

            course.get().getUsers_enrolled().add(user.get());
            courseService.saveCourseModel(course.get());

            return ResponseEntity.ok("User enrolled successfully in " + course.get().getName());
        } else {
            return ResponseEntity.badRequest().body("Invalid user or course.");
        }*/
