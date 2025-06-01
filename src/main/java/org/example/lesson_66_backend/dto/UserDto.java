package org.example.lesson_66_backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private String name;
    private String password;
    private String phone;
    private String position;
}
