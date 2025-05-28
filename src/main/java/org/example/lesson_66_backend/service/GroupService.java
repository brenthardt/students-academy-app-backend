package org.example.lesson_66_backend.service;

import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.type.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface GroupService {
    List<Group> findAll();
    Group saveGroup(Group group);
    ResponseEntity<?> deleteGroup(UUID id);
    ResponseEntity<?> updateGroup(UUID id, Group group);
ResponseEntity<?> assignStudents(UUID groupId, List<UUID> studentIds);
    ResponseEntity<?> assignMentor(UUID groupId, List<UUID> mentorIds);
    void removeStudent(UUID groupId, UUID studentIds);
    void removeMentor(UUID groupId, UUID mentorIds);
    List<Group> findByType(Type type);
    List<Group> findByMentorId(UUID id);
    List<Group> findByCourseId(UUID courseId);
List<Group> findByDayType(String dayType);
List<Group> findByStartTime(LocalDate startTime);
    List<Group> findByEndTime(LocalDate endTime);
    ResponseEntity<List<LocalDate>> getAllStartDates();
    ResponseEntity<List<LocalDate>> getAllEndDates();
    Optional<Group> findById(UUID id);



}
