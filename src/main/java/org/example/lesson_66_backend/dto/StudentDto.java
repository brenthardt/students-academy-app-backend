package org.example.lesson_66_backend.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class StudentDto {
    private UUID id;
    private String name;
    private String phone;
}

