package com.hospitalmanagement.hospital.management.api;

import com.hospitalmanagement.hospital.management.api.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userDAO.getUserByUsername("admin") == null) {
            String hashedPassword = passwordEncoder.encode("admin123");
            userDAO.addUser("admin", hashedPassword, "ADMIN",1);
            System.out.println(">>> System Initialized: Default admin account created (admin / admin123)");
        } else {
            System.out.println(">>> DataInitializer: Admin user already exists.");
        }
    }
}