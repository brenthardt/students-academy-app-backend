package org.example.lesson_66_backend.service;


import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.entity.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<Room> findAll();
    Room saveRoom(Room room);
    ResponseEntity<?> deleteRoom(UUID id);
    ResponseEntity<?> updateRoom(UUID id, Room room);


}
