package com.hardware.config;

import com.hardware.entity.User;
import com.hardware.enums.Role;
import com.hardware.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*
 * =====================================================================
 * WHAT IS A DATA SEEDER?
 * =====================================================================
 * A Data Seeder is a class that runs automatically every time the
 * application starts. Its job is to check whether essential default
 * data exists in the database, and create it if it does not.
 *
 * WHY DO WE NEED THIS?
 * When someone sets up this project for the first time, the database
 * will be completely empty. Without a default admin account, nobody
 * can log in to create other users. The DataSeeder solves this by
 * automatically creating a default admin user on the very first run.
 *
 * HOW DOES IT WORK?
 * Spring Boot provides the "ApplicationRunner" interface. Any class
 * that implements it will have its "run()" method called automatically
 * after the Spring application context is fully loaded and the database
 * connection is ready — making it the perfect place to seed data.
 *
 * IMPORTANT: The seeder checks FIRST before creating anything. If the
 * admin already exists it does nothing. So it is completely safe to
 * run on every startup — it will never create duplicate records.
 * =====================================================================
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
     * This method is called automatically by Spring Boot after the
     * application has fully started and the database is ready.
     */
    @Override
    public void run(ApplicationArguments args) {
        seedAdminUser();
    }

    private void seedAdminUser() {

        /*
         * STEP 1 — Check if an admin user already exists.
         *
         * We look for the username "admin" in the database.
         * If found, we skip creation entirely and log a message.
         * This prevents duplicate admin accounts on every restart.
         */
        if (userRepository.existsByUsername("admin")) {
            log.info("DataSeeder: Admin user already exists. Skipping.");
            return;
        }

        /*
         * STEP 2 — Build the default admin user.
         *
         * Username : admin
         * Password : admin123  ← stored as BCrypt hash, never plain text
         * Role     : ADMIN     ← full access to all endpoints
         * Active   : true      ← account is enabled immediately
         *
         * ⚠ IMPORTANT: Change this password immediately after the first
         * login. Leaving default credentials in production is a serious
         * security risk.
         */
        User admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .active(true)
                .build();

        /*
         * STEP 3 — Save the admin user to the database.
         *
         * Hibernate will INSERT a new row into the "users" table.
         * The password is already BCrypt-hashed at this point, so it
         * is safe to store.
         */
        userRepository.save(admin);

        /*
         * STEP 4 — Log a clear reminder to change the password.
         *
         * @Slf4j (Lombok) gives us the "log" object automatically.
         * These messages appear in the IntelliJ console when you run
         * the app, so you will see them immediately on first startup.
         */
        log.info("================================================================");
        log.info("  DataSeeder: Default admin user created.");
        log.info("  Username : admin");
        log.info("  Password : admin123");
        log.info("  ⚠  Change this password immediately after first login!");
        log.info("================================================================");
    }
}
