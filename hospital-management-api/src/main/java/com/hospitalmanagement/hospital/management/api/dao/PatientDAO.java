package com.hospitalmanagement.hospital.management.api.dao;

import com.hospitalmanagement.hospital.management.api.db.Database;
import com.hospitalmanagement.hospital.management.api.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    static {
        // Sigurohet automatikisht që kolona username ekziston në databazë sa herë ngarkohet kjo klasë
        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("ALTER TABLE patients ADD COLUMN IF NOT EXISTS username VARCHAR(255);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> getPatientsByUsername(String username) throws SQLException {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Patient(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("disease"),
                            rs.getString("admit_date"),
                            rs.getString("username")
                    ));
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
                    return new Patient(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getString("disease"),
                            rs.getString("admit_date"),
                            rs.getString("username")
                    );
                }
            }
        }
        return null;
    }

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, age, gender, phone, address, disease, admit_date, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getDisease());
            stmt.setString(7, patient.getAdmitDate());
            stmt.setString(8, patient.getUsername());
            stmt.executeUpdate();
        }
    }

    public void updatePatient(Patient patient) throws SQLException {
        String sql = "UPDATE patients SET name = ?, age = ?, gender = ?, phone = ?, address = ?, disease = ?, admit_date = ? WHERE id = ? AND username = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getPhone());
            stmt.setString(5, patient.getAddress());
            stmt.setString(6, patient.getDisease());
            stmt.setString(7, patient.getAdmitDate());
            stmt.setInt(8, patient.getId());
            stmt.setString(9, patient.getUsername());
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