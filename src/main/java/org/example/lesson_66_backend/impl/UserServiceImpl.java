package org.example.lesson_66_backend.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.dto.LoginDto;
import org.example.lesson_66_backend.dto.LoginResponse;
import org.example.lesson_66_backend.dto.UserDto;
import org.example.lesson_66_backend.entity.Group;
import org.example.lesson_66_backend.entity.User;
import org.example.lesson_66_backend.jwt.JwtUtil;
import org.example.lesson_66_backend.repository.GroupRepository;
import org.example.lesson_66_backend.repository.RoleRepository;
import org.example.lesson_66_backend.repository.UserRepository;
import org.example.lesson_66_backend.role.Role;
import org.example.lesson_66_backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> findAll() {
      return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public User save(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
            user.setRoles(List.of(userRole));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> update(UUID id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setPhone(user.getPhone());
                    existingUser.setPosition(user.getPosition());
                    existingUser.setPayment(user.getPayment());
                    if (!user.getPassword().equals(existingUser.getPassword())) {
                        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }

                    existingUser.setRoles(user.getRoles());
                    return ResponseEntity.ok(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @Override
    public ResponseEntity<?> signUp(UserDto userDto) {
        if (userRepository.existsByPhone(userDto.getPhone())) {
            return ResponseEntity.badRequest().body("User already exists with this phone");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
user.setPosition(userDto.getPosition());
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        user.setRoles(List.of(userRole));

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getPhone(), "ROLE_USER");
        String refreshToken = jwtUtil.generateRefreshToken(savedUser.getPhone(), "ROLE_USER");

        savedUser.setRefreshToken(refreshToken);
        userRepository.save(savedUser);

        LoginResponse response = new LoginResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getPhone(),
                "ROLE_USER",
                token,
                refreshToken,
                savedUser.getPosition()
        );

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> login(LoginDto loginDTO) {
        return userRepository.findByPhone(loginDTO.getPhone())
                .map(user -> {
                    if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                        String mainRole = user.getRoles().stream()
                                .findFirst()
                                .map(Role::getName)
                                .orElse("UNKNOWN");

                        String token = jwtUtil.generateToken(user.getPhone(), mainRole);
                        String refreshToken = jwtUtil.generateRefreshToken(user.getPhone(), mainRole);

                        user.setRefreshToken(refreshToken);
                        userRepository.save(user);

                        LoginResponse response = new LoginResponse(
                                user.getId(),
                                user.getName(),
                                user.getPhone(),
                                mainRole,
                                token,
                                refreshToken,
                                user.getPosition()
                        );

                        return ResponseEntity.ok(response);
                    } else {
                        return ResponseEntity.badRequest().body("Invalid password");
                    }
                })
                .orElse(ResponseEntity.badRequest().body("User not found"));
    }

    @Override
    public ResponseEntity<?> deleteByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok("✅ User deleted");
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("❌ User not found"));
    }

    @Override
    public ResponseEntity<?> logout(String phone) {
        return ResponseEntity.ok("User logged out successfully");
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        try {

            System.out.println("🔄 Incoming refresh token: '" + refreshToken + "'");


            Claims claims = jwtUtil.getClaims(refreshToken);
            String phone = claims.getSubject();
            String role = claims.get("role", String.class);
            System.out.println("📱 Phone from token: " + phone + ", Role: " + role);


            userRepository.findAll().forEach(u -> {
                System.out.println("👤 User in DB: ID=" + u.getId() + ", phone=" + u.getPhone() + ", token='" + u.getRefreshToken() + "'");
            });


            User user = userRepository.findByRefreshToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("❌ Invalid refresh token (not found in DB)"));


            if (!user.getPhone().equals(phone)) {
                System.out.println("⚠️ Token subject mismatch. Token phone: " + phone + ", DB phone: " + user.getPhone());
                return ResponseEntity.status(401).body("Token subject mismatch");
            }


            String newAccessToken = jwtUtil.generateToken(phone, role);


            String newRefreshToken = refreshToken;


//             String newRefreshToken = jwtUtil.generateRefreshToken(phone, role);
//             user.setRefreshToken(newRefreshToken);


            user.setRefreshToken(newRefreshToken);
            userRepository.save(user);
            System.out.println("✅ New tokens generated and saved");


            return ResponseEntity.ok(
                    new LoginResponse(
                            user.getId(),
                            user.getName(),
                            user.getPhone(),
                            role,
                            newAccessToken,
                            newRefreshToken,
                            user.getPosition()
                    )
            );

        } catch (Exception e) {
            System.out.println("❌ Exception during refresh: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(403).body("Refresh token expired or invalid");
        }
    }

@Override
    public List<User> getMentors() {
        return userRepository.findAll()
                .stream()
                .filter(user -> "mentor".equalsIgnoreCase(user.getPosition()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> uP(UUID id, Map<String, Integer> paymentRequest) {

        return userRepository.findById(id)
                .map(user -> {
                    if (!"student".equalsIgnoreCase(user.getPosition())) {
                        return ResponseEntity.badRequest().body("Only students can make payments");
                    }

                    if (user.getGroup() == null) {
                        return ResponseEntity.badRequest().body("Student is not in any group");
                    }

                    Integer paymentAmount = paymentRequest.get("payment");
                    if (paymentAmount == null) {
                        return ResponseEntity.badRequest().body("Payment amount is required");
                    }

                    if (!paymentAmount.equals(user.getGroup().getPrice())) {
                        return ResponseEntity.badRequest().body(
                                String.format("Payment must be exactly %d$", user.getGroup().getPrice()));
                    }

                    user.setPayment(paymentAmount);
                    userRepository.save(user);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }



}
