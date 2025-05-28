package org.example.lesson_66_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.TimeTable;
import org.example.lesson_66_backend.repository.TimeTableRepository;
import org.example.lesson_66_backend.service.TimeTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeTableServiceImpl implements TimeTableService {
    private final TimeTableRepository timeTableRepository;

    @Override
    public List<TimeTable> findAll() {
        return timeTableRepository.findAll();
    }

    @Override
    public TimeTable saveTimeTable(TimeTable timeTable) {
        return timeTableRepository.save(timeTable);
    }

    @Override
    public ResponseEntity<?> deleteTimeTable(UUID id) {
        if(timeTableRepository.existsById(id)){
            timeTableRepository.deleteById(id);
            return ResponseEntity.ok("TimeTable deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> updateTimeTable(UUID id, TimeTable timeTable) {
        return timeTableRepository.findById(id).map(existingTimeTable->{
                    existingTimeTable.setGroup(timeTable.getGroup());
                    existingTimeTable.setTitle(timeTable.getTitle());
                    existingTimeTable.setPrice(timeTable.getPrice());
                    existingTimeTable.setStartDate(timeTable.getStartDate());
                    existingTimeTable.setEndDate(timeTable.getEndDate());
                    existingTimeTable.setCurrentMentorId(timeTable.getCurrentMentorId());
                    return ResponseEntity.ok(timeTableRepository.save((existingTimeTable)));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
