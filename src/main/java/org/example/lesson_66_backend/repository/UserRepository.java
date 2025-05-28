package org.example.lesson_66_backend.repository;

import org.example.lesson_66_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByName(String name);
    Optional<User> findByPhone(String phone);
    boolean existsByPhone(String phone);
    Optional<User> findByRefreshToken(String refreshToken);

    List<User> findAllByPosition(String student);
}
