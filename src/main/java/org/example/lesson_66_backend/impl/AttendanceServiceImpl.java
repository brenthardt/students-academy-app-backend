package org.example.lesson_66_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.dto.AttendanceDto;
import org.example.lesson_66_backend.entity.Attendance;
import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.entity.User;
import org.example.lesson_66_backend.repository.AttendanceRepository;
import org.example.lesson_66_backend.repository.GroupRepository;
import org.example.lesson_66_backend.repository.UserRepository;
import org.example.lesson_66_backend.service.AttendanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final GroupRepository groupRepo;
    private final UserRepository userRepo;

    @Override
    public void saveAll(List<AttendanceDto> list) {
        List<Attendance> attendances = list.stream().map(dto -> {
            User student = userRepo.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found: " + dto.getStudentId()));

            Group group = groupRepo.findById(dto.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found: " + dto.getGroupId()));


            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setGroup(group);
            attendance.setDate(dto.getDate());
            attendance.setPresent(dto.isPresent());

            return attendance;
        }).toList();

        attendanceRepo.saveAll(attendances);
    }


    @Override
    public List<Attendance> getByGroup(UUID groupId) {
        return attendanceRepo.findByGroupId(groupId);
    }
}

