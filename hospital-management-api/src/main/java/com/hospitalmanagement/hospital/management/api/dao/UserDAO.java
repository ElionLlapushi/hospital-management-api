package com.hospitalmanagement.hospital.management.api.dao;

import com.hospitalmanagement.hospital.management.api.db.Database;
import com.hospitalmanagement.hospital.management.api.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserDAO {

    public int addUser(String username, String hashedPassword, String role, int clinicId) throws SQLException {
        // Nese kolona clinic_id nuk eshte krijuar ende ne databaze, perdorim insert pa te
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // E marrim clinic_id me 1 manualisht nese kolona mungon ne databaze per momentin
                    int clinicId = 1;
                    try {
                        clinicId = rs.getInt("clinic_id");
                    } catch (Exception e) {
                        // Ignoron gabimin nese kolona nuk ekziston dhe perdor vleren 1
                    }

                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            clinicId
                    );
                }
            }
        }
        return null;
    }
}