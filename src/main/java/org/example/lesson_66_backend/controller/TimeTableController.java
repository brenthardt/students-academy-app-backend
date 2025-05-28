package org.example.lesson_66_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.TimeTable;
import org.example.lesson_66_backend.service.TimeTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/timetables")
@RequiredArgsConstructor
public class TimeTableController {
    private final TimeTableService timeTableService;

    @GetMapping
    public List<TimeTable> findAll() {
        return timeTableService.findAll();
    }

    @PostMapping
    public TimeTable saveTT(@RequestBody TimeTable timeTable) {
        return timeTableService.saveTimeTable(timeTable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTT(@PathVariable UUID id) {
        return ResponseEntity.ok(timeTableService.deleteTimeTable(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTT(@PathVariable UUID id, @RequestBody TimeTable timeTable) {
        return timeTableService.updateTimeTable(id, timeTable);
    }

}
