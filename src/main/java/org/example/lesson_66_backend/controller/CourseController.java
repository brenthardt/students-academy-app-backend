package org.example.lesson_66_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.Course;
import org.example.lesson_66_backend.entity.Course;
import org.example.lesson_66_backend.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @PostMapping
    public Course saveCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable UUID id){
        return courseService.deleteCourse(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody Course course, @PathVariable UUID id){
        return courseService.updateCourse(id, course);
    }


}
