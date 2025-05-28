package org.example.lesson_66_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.entity.User;
import org.example.lesson_66_backend.repository.GroupRepository;
import org.example.lesson_66_backend.repository.UserRepository;
import org.example.lesson_66_backend.service.GroupService;
import org.example.lesson_66_backend.type.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group saveGroup(Group group){
      return groupRepository.save(group);
    }

    @Override
    public ResponseEntity<?> deleteGroup(UUID id) {
        if(groupRepository.existsById(id)){
            groupRepository.deleteById(id);
            return ResponseEntity.ok("Group deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> updateGroup(UUID id, Group group) {
        return groupRepository.findById(id).map(existingGroup->{
            existingGroup.setName(group.getName());
            existingGroup.setDayType(group.getDayType());
            existingGroup.setStartTime(group.getStartTime());
            existingGroup.setEndTime(group.getEndTime());
            existingGroup.setRoom(group.getRoom());
            existingGroup.setCourse(group.getCourse());
                    existingGroup.setPrice(group.getPrice());

                    existingGroup.setTypes(group.getTypes());

            return ResponseEntity.ok(groupRepository.save((existingGroup)));
        })
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @Override
    public ResponseEntity<?> assignStudents(UUID groupId, List<UUID> studentIds) {
        return groupRepository.findById(groupId).map(group -> {

            List<User> studentsToAssign = userRepository.findAllById(studentIds);

            for (User student : studentsToAssign) {

                if (student.getGroup() == null || !student.getGroup().getId().equals(groupId)) {
                    student.setGroup(group);
                }
            }

            userRepository.saveAll(studentsToAssign);

            return ResponseEntity.ok("Students assigned to group");
        }).orElse(ResponseEntity.notFound().build());
    }

    @Transactional
    @Override
    public ResponseEntity<?> assignMentor(UUID groupId, List<UUID> mentorIds) {
        return groupRepository.findById(groupId).map(group -> {

            List<User> mentorsToAssign = userRepository.findAllById(mentorIds);

            for (User mentor : mentorsToAssign) {

                if (mentor.getGroup() == null || !mentor.getGroup().getId().equals(groupId)) {
                    mentor.setGroup(group);
                }
            }

            userRepository.saveAll(mentorsToAssign);

            return ResponseEntity.ok("Mentor assigned to group");
        }).orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public void removeStudent(UUID groupId, UUID studentId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        User student = userRepository.findById(studentId).orElseThrow();

        if (student.getGroup() != null && student.getGroup().getId().equals(groupId)) {
            student.setGroup(null);
            userRepository.save(student);
        }
    }

    @Override
    @Transactional
    public void removeMentor(UUID groupId, UUID mentorId) {
        Group group = groupRepository.findById(groupId).orElseThrow();
        User mentor = userRepository.findById(mentorId).orElseThrow();

        if (mentor.getGroup() != null && mentor.getGroup().getId().equals(groupId)) {
            mentor.setGroup(null);
            userRepository.save(mentor);
            group.getMentors().removeIf(m -> m.getId().equals(mentorId));
        }
    }

    @Override
    public List<Group> findByType(Type type) {
        return groupRepository.findByTypes(type);
    }

    public List<Group> findByMentorId(UUID id) {
        return groupRepository.findAll().stream()
                .filter(group -> group.getMentors().stream()
                        .anyMatch(mentor -> mentor.getId().equals(id)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> findByCourseId(UUID courseId) {
        return groupRepository.findAll().stream()
                .filter(group -> group.getCourse() != null && group.getCourse().getId().equals(courseId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Group> findByDayType(String dayType) {
        return groupRepository.findByDayType(dayType);
    }

    @Override
    public List<Group> findByStartTime(LocalDate startTime) {
        return groupRepository.findByStartTime(startTime);
    }

    @Override
    public List<Group> findByEndTime(LocalDate endTime) {
        return groupRepository.findByEndTime(endTime);
    }

@Override
    public ResponseEntity<List<LocalDate>> getAllStartDates() {
        List<LocalDate> dates = groupRepository.findAll().stream()
                .map(Group::getStartTime)
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(dates);
    }

@Override
    public ResponseEntity<List<LocalDate>> getAllEndDates() {
        List<LocalDate> dates = groupRepository.findAll().stream()
                .map(Group::getEndTime)
                .distinct()
                .collect(Collectors.toList());
        return ResponseEntity.ok(dates);
    }

    @Override
    public Optional<Group> findById(UUID id) {
        return groupRepository.findById(id);
    }









}
