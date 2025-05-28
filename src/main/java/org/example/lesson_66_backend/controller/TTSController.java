package org.example.lesson_66_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.TimeTableStudent;
import org.example.lesson_66_backend.service.TTSService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tts")
@RequiredArgsConstructor
public class TTSController {

    private final TTSService ttsService;

    @GetMapping
    public List<TimeTableStudent> findAll() {
        return ttsService.findAll();
    }

}
