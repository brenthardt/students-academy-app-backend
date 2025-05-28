package org.example.lesson_66_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.lesson_66_backend.status.Status;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tts")
public class TimeTableStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private TimeTable timeTable;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User user;
    private Integer price;
    private Status status;
}
