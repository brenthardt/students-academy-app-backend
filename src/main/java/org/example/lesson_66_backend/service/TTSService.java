package org.example.lesson_66_backend.service;

import org.example.lesson_66_backend.entity.TimeTableStudent;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TTSService {
    List<TimeTableStudent> findAll();
}
