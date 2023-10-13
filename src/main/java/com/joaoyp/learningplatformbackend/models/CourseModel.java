package com.joaoyp.learningplatformbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_courses")
@Data
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String instructor;
    private String duration;
    private BigDecimal price;

    @ManyToMany(mappedBy = "enrolledCourses")
    private List<UserModel> users_enrolled = new ArrayList<>();
}