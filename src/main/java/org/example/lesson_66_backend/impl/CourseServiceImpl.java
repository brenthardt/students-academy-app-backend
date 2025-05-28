package org.example.lesson_66_backend.impl;

import lombok.RequiredArgsConstructor;

import org.example.lesson_66_backend.entity.Course;
import org.example.lesson_66_backend.repository.CourseRepository;
import org.example.lesson_66_backend.repository.GroupRepository;
import org.example.lesson_66_backend.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }



    @Override
    public ResponseEntity<?> deleteCourse(UUID id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        if (!groupRepository.findAllByRoomId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete course: it is still assigned to groups.");
        }

        courseRepository.deleteById(id);
        return ResponseEntity.ok("Course deleted");
    }


    @Override
    public ResponseEntity<?> updateCourse(UUID id, Course course) {
        return courseRepository.findById(id).map(existingCourse->{
                    existingCourse.setName(course.getName());
                    return ResponseEntity.ok(courseRepository.save((existingCourse)));
                })
                .orElse(ResponseEntity.notFound().build());
    }


}
