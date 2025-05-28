package org.example.lesson_66_backend.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.lesson_66_backend.entity.User;
import org.example.lesson_66_backend.repository.RoleRepository;
import org.example.lesson_66_backend.repository.UserRepository;
import org.example.lesson_66_backend.role.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdminUser() {

        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));

        String adminPhone = "1056";
        if (userRepository.findByPhone(adminPhone).isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setPhone(adminPhone);
            admin.setPassword(passwordEncoder.encode("1056"));
            admin.setRoles(List.of(adminRole));

            userRepository.save(admin);
        }
    }
}

