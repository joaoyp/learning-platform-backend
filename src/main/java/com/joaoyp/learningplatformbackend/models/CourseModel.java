package com.joaoyp.learningplatformbackend.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private String difficulty;
    @Column(columnDefinition = "TEXT")
    private String imagePath;
    private double rating;
    private String category;
    private LocalDateTime created_at;
    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_courses_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<UserModel> users_enrolled;

    @Override
    public String toString() {
        return name;
    }

}