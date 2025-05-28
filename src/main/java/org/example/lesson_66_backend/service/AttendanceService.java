package org.example.lesson_66_backend.service;

import org.example.lesson_66_backend.dto.AttendanceDto;
import org.example.lesson_66_backend.entity.Attendance;

import java.util.List;
import java.util.UUID;

public interface AttendanceService {
    void saveAll(List<AttendanceDto> list);
    List<Attendance> getByGroup(UUID groupId);
}

