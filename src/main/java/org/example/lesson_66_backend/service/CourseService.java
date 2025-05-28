package org.example.lesson_66_backend.service;


import org.example.lesson_66_backend.entity.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    List<Course> findAll();
    Course saveCourse(Course course);
    ResponseEntity<?> deleteCourse(UUID id);
    ResponseEntity<?> updateCourse(UUID id, Course course);
}
