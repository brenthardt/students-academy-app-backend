package org.example.lesson_66_backend.dto;


import lombok.Data;
import org.example.lesson_66_backend.type.Type;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class GroupDto {
    private UUID id;
    private String name;
    private String dayType;
    private LocalDate startTime;
    private LocalDate endTime;
    private List<StudentDto> students;
    private List<MentorDto> mentors;
    private Type types;
    private Integer price;
}

