package org.example.lesson_66_backend.service;

import org.example.lesson_66_backend.dto.LoginDto;
import org.example.lesson_66_backend.dto.UserDto;
import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public interface UserService {
    ResponseEntity<?> findAll();
    User save(User user);
    ResponseEntity<?> delete(UUID id);
    ResponseEntity<?> update(UUID id, User user);
    ResponseEntity<?> signUp(UserDto userDto);
    ResponseEntity<?> login(LoginDto loginDto);
    ResponseEntity<?> deleteByPhone(String phone);
    ResponseEntity<?> logout(String phone);
    ResponseEntity<?> refreshToken(String refreshToken);
    List<User> getMentors();
    ResponseEntity<?> uP(UUID id, Map<String, Integer> paymentRequest);
}
