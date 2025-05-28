package org.example.lesson_66_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.service.GroupService;
import org.example.lesson_66_backend.type.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public List<Group> findAll(){
        return groupService.findAll();
    }

    @PostMapping
    public Group saveGroup(@RequestBody Group group) {
        return groupService.saveGroup(group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable UUID id) {
        return groupService.deleteGroup(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable UUID id, @RequestBody Group group) {
        return groupService.updateGroup(id, group);
    }

    @PostMapping("/{groupId}/as")
    public ResponseEntity<?> assignStudentsToGroup(@PathVariable UUID groupId, @RequestBody List<UUID> studentIds) {
        return groupService.assignStudents(groupId, studentIds);
    }

    @PostMapping("/{groupId}/am")
    public ResponseEntity<?> assignMentorToGroup(@PathVariable UUID groupId, @RequestBody List<UUID> mentorIds) {
        return groupService.assignMentor(groupId, mentorIds);
    }

    @DeleteMapping("/{groupId}/students/{studentId}")
    public ResponseEntity<Void> removeStudentFromGroup(@PathVariable UUID groupId, @PathVariable UUID studentId) {
        groupService.removeStudent(groupId, studentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{groupId}/m/{mentorId}")
    public ResponseEntity<Void> removeMentorFromGroup(@PathVariable UUID groupId, @PathVariable UUID mentorId) {
        groupService.removeMentor(groupId, mentorId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/type")
    public ResponseEntity<List<Group>> getGroupsByType(@RequestParam Type type) {
        return ResponseEntity.ok(groupService.findByType(type));
    }

    @GetMapping("/mn")
    public ResponseEntity<List<Group>> getGroupsByMentor(@RequestParam UUID id) {
        return ResponseEntity.ok(groupService.findByMentorId(id));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Group>> getGroupsByCourse(@PathVariable UUID courseId) {
        return ResponseEntity.ok(groupService.findByCourseId(courseId));
    }

    @GetMapping("/daytype")
    public ResponseEntity<List<Group>> getGroupsByDayType(@RequestParam String dayType) {
        return ResponseEntity.ok(groupService.findByDayType(dayType));
    }

    @GetMapping("/st")
    public ResponseEntity<List<Group>> sd(@RequestParam LocalDate startTime) {
        return ResponseEntity.ok(groupService.findByStartTime(startTime));
    }

    @GetMapping("/et")
    public ResponseEntity<List<Group>> ed(@RequestParam LocalDate endTime) {
        return ResponseEntity.ok(groupService.findByEndTime(endTime));
    }

    @GetMapping("/st/list")
    public ResponseEntity<List<LocalDate>> getAllStartDates() {
       return groupService.getAllStartDates();
    }

    @GetMapping("/et/list")
    public ResponseEntity<List<LocalDate>> getAllEndDates() {
      return groupService.getAllEndDates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable UUID id) {
        return groupService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
