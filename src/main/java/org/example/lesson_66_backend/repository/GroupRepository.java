package org.example.lesson_66_backend.repository;

import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.type.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
    List<Group> findAllByRoomId(UUID roomId);
    List<Group> findByTypes(Type type);
    @Query("SELECT g FROM Group g JOIN g.mentors m WHERE m.id = :id")
    List<Group> findByMentorId(@Param("id") UUID id);
    List<Group> findByCourse_Id(UUID courseId);
    List<Group> findByDayType(String dayType);
List<Group> findByStartTime(LocalDate startTime);
    List<Group> findByEndTime(LocalDate endTime);



}
