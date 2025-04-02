package com.dong.authservice.config;

import com.dong.authservice.entity.Role;
import com.dong.authservice.entity.User;
import com.dong.authservice.repository.RoleRepository;
import com.dong.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing roles and admin user...");
        initRoles();
        initAdminUser();
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            log.info("Creating roles...");
            
            Role userRole = new Role();
            userRole.setName(Role.ERole.ROLE_USER);
            roleRepository.save(userRole);

            Role modRole = new Role();
            modRole.setName(Role.ERole.ROLE_MODERATOR);
            roleRepository.save(modRole);

            Role adminRole = new Role();
            adminRole.setName(Role.ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
            
            log.info("Roles created successfully");
        }
    }

    private void initAdminUser() {
        if (!userRepository.existsByUsername("admin")) {
            log.info("Creating admin user...");
            
            Set<Role> roles = new HashSet<>();
            Role adminRole = roleRepository.findByName(Role.ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Admin Role not found."));
            roles.add(adminRole);

            User admin = User.builder()
                    .username("admin")
                    .email("admin@mastershop.com")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("Admin")
                    .lastName("User")
                    .roles(roles)
                    .enabled(true)
                    .build();

            userRepository.save(admin);
            
            log.info("Admin user created successfully");
        }
    }
}