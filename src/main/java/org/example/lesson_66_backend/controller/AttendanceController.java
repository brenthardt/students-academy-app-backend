package org.example.lesson_66_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.dto.AttendanceDto;
import org.example.lesson_66_backend.entity.Attendance;
import org.example.lesson_66_backend.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<?> saveAll(@RequestBody List<AttendanceDto> list) {
        attendanceService.saveAll(list);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/groups/{groupId}")
    public ResponseEntity<List<Attendance>> getByGroup(@PathVariable UUID groupId) {
        return ResponseEntity.ok(attendanceService.getByGroup(groupId));
    }
}

