package org.example.lesson_66_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.TimeTableStudent;
import org.example.lesson_66_backend.repository.TTSRepository;
import org.example.lesson_66_backend.service.TTSService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TTSServiceImpl implements TTSService {

    private final TTSRepository ttsRepository;

    @Override
    public List<TimeTableStudent> findAll() {
        return ttsRepository.findAll();
    }
}
