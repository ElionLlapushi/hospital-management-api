package com.hospitalmanagement.hospital.management.api.dao;

import com.hospitalmanagement.hospital.management.api.db.Database;
import com.hospitalmanagement.hospital.management.api.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    static {
        // Siguron që të gjitha kolonat e reja ekzistojnë në tabelën patients
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS username VARCHAR(255);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS email VARCHAR(255);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS blood_group VARCHAR(10);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS allergies TEXT;");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS emergency_contact VARCHAR(255);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS emergency_phone VARCHAR(50);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS date_of_birth VARCHAR(50);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS medical_history TEXT;");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS insurance_number VARCHAR(100);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS occupation VARCHAR(100);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS weight DOUBLE PRECISION;");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS height DOUBLE PRECISION;");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS status VARCHAR(50);");
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS photo_url TEXT;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Patient mapResultSetToPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("phone"),
                rs.getString("address"),
                rs.getString("disease"),
                rs.getString("admit_date"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("blood_group"),
                rs.getString("allergies"),
                rs.getString("emergency_contact"),
                rs.getString("emergency_phone"),
                rs.getString("date_of_birth"),
                rs.getString("medical_history"),
                rs.getString("insurance_number"),
                rs.getString("occupation"),
                rs.getDouble("weight"),
                rs.getDouble("height"),
                rs.getString("status"),
                rs.getString("photo_url")
        );
    }

    public List<Patient> getPatientsByUsername(String username) throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToPatient(rs));
                }
            }
        }
        return list;
    }

    public Patient getPatientByIdAndUsername(int id, String username) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ? AND username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatient(rs);
                }
            }
        }
        return null;
    }

    public void addPatient(Patient p) throws SQLException {
        String sql = "INSERT INTO patients (name, age, gender, phone, address, disease, admit_date, username, " +
                "email, blood_group, allergies, emergency_contact, emergency_phone, date_of_birth, " +
                "medical_history, insurance_number, occupation, weight, height, status, photo_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getPhone());
            stmt.setString(5, p.getAddress());
            stmt.setString(6, p.getDisease());
            stmt.setString(7, p.getAdmitDate());
            stmt.setString(8, p.getUsername());
            stmt.setString(9, p.getEmail());
            stmt.setString(10, p.getBloodGroup());
            stmt.setString(11, p.getAllergies());
            stmt.setString(12, p.getEmergencyContact());
            stmt.setString(13, p.getEmergencyPhone());
            stmt.setString(14, p.getDateOfBirth());
            stmt.setString(15, p.getMedicalHistory());
            stmt.setString(16, p.getInsuranceNumber());
            stmt.setString(17, p.getOccupation());
            stmt.setDouble(18, p.getWeight());
            stmt.setDouble(19, p.getHeight());
            stmt.setString(20, p.getStatus());
            stmt.setString(21, p.getPhotoUrl());
            stmt.executeUpdate();
        }
    }

    public void updatePatient(Patient p) throws SQLException {
        String sql = "UPDATE patients SET name = ?, age = ?, gender = ?, phone = ?, address = ?, disease = ?, " +
                "admit_date = ?, email = ?, blood_group = ?, allergies = ?, emergency_contact = ?, " +
                "emergency_phone = ?, date_of_birth = ?, medical_history = ?, insurance_number = ?, " +
                "occupation = ?, weight = ?, height = ?, status = ?, photo_url = ? " +
                "WHERE id = ? AND username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getName());
            stmt.setInt(2, p.getAge());
            stmt.setString(3, p.getGender());
            stmt.setString(4, p.getPhone());
            stmt.setString(5, p.getAddress());
            stmt.setString(6, p.getDisease());
            stmt.setString(7, p.getAdmitDate());
            stmt.setString(8, p.getEmail());
            stmt.setString(9, p.getBloodGroup());
            stmt.setString(10, p.getAllergies());
            stmt.setString(11, p.getEmergencyContact());
            stmt.setString(12, p.getEmergencyPhone());
            stmt.setString(13, p.getDateOfBirth());
            stmt.setString(14, p.getMedicalHistory());
            stmt.setString(15, p.getInsuranceNumber());
            stmt.setString(16, p.getOccupation());
            stmt.setDouble(17, p.getWeight());
            stmt.setDouble(18, p.getHeight());
            stmt.setString(19, p.getStatus());
            stmt.setString(20, p.getPhotoUrl());
            stmt.setInt(21, p.getId());
            stmt.setString(22, p.getUsername());
            stmt.executeUpdate();
        }
    }

    public void deletePatient(int id) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public int getTodayPatientsCountByUsername(String todayDate, String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM patients WHERE admit_date = ? AND username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, todayDate);
            stmt.setString(2, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}