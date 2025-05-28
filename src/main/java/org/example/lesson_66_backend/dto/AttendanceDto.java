package org.example.lesson_66_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AttendanceDto {
    private UUID studentId;
    private UUID groupId;
    private LocalDate date;
    private boolean present;
}

