package org.example.lesson_66_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.Room;
import org.example.lesson_66_backend.repository.GroupRepository;
import org.example.lesson_66_backend.repository.RoomRepository;
import org.example.lesson_66_backend.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final GroupRepository groupRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public ResponseEntity<?> deleteRoom(UUID id) {
        if (!roomRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        if (!groupRepository.findAllByRoomId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete room: it is still assigned to groups.");
        }

        roomRepository.deleteById(id);
        return ResponseEntity.ok("Room deleted");
    }


    @Override
    public ResponseEntity<?> updateRoom(UUID id, Room room) {
        return roomRepository.findById(id).map(existingRoom->{
                    existingRoom.setName(room.getName());
                    return ResponseEntity.ok(roomRepository.save((existingRoom)));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
