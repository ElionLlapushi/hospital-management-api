package com.hospitalmanagement.hospital.management.api.dao;

import com.hospitalmanagement.hospital.management.api.model.Clinic;
import com.hospitalmanagement.hospital.management.api.db.Database;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClinicDAO {

    public List<Clinic> getAllClinics() {
        List<Clinic> clinics = new ArrayList<>();
        String query = "SELECT * FROM clinics";

        try (Connection conn = Database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Clinic clinic = new Clinic(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("subscription_status")
                );
                clinics.add(clinic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clinics;
    }
}
