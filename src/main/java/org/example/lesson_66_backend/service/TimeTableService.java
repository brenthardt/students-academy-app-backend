package org.example.lesson_66_backend.service;

import org.example.lesson_66_backend.entity.TimeTable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TimeTableService {
List<TimeTable> findAll();
    TimeTable saveTimeTable(TimeTable timeTable);
    ResponseEntity<?> deleteTimeTable(UUID id);
    ResponseEntity<?> updateTimeTable(UUID id, TimeTable timeTable);
}
