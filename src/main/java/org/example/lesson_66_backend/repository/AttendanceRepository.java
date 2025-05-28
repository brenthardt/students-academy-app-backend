package org.example.lesson_66_backend.repository;

import org.example.lesson_66_backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    List<Attendance> findByGroupIdAndDate(UUID groupId, LocalDate date);
    List<Attendance> findByGroupId(UUID groupId);
}

