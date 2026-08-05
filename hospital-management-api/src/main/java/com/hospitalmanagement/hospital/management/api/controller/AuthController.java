package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.UserDAO;
import com.hospitalmanagement.hospital.management.api.model.User;
import com.hospitalmanagement.hospital.management.api.model.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDAO userDAO = new UserDAO();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> body) throws SQLException {
        String username = body.get("username");
        String password = body.get("password");
        String role = body.getOrDefault("role", "STAFF");

        // Marrim clinicId nga trupi i kërkesës, ose e vendosim 1 si parazgjedhje
        int clinicId = 1;
        if (body.containsKey("clinicId")) {
            try {
                clinicId = Integer.parseInt(body.get("clinicId"));
            } catch (NumberFormatException e) {
                // Përdor vlerën parazgjedhje nëse dështon konvertimi
            }
        }

        String hashedPassword = passwordEncoder.encode(password);
        userDAO.addUser(username, hashedPassword, role, clinicId);

        return "Përdoruesi u regjistrua me sukses.";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) throws SQLException {
        String username = body.get("username");
        String password = body.get("password");

        User user = userDAO.getUserByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Username ose password i pasaktë.");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getClinicId());
        return Map.of("token", token);
    }
}