package com.hospitalmanagement.hospital.management.api.dao;

import com.hospitalmanagement.hospital.management.api.db.Database;
import com.hospitalmanagement.hospital.management.api.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    public int addBill(int patientId, String description, double amount, String billDate) throws SQLException {
        String sql = "INSERT INTO bills (patient_id, description, amount, paid, bill_date) VALUES (?, ?, ?, 0, ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, patientId);
            stmt.setString(2, description);
            stmt.setDouble(3, amount);
            stmt.setString(4, billDate);
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    private static final String SELECT_WITH_NAME =
            "SELECT b.id, b.patient_id, p.name AS patient_name, b.description, b.amount, b.paid, b.bill_date " +
                    "FROM bills b JOIN patients p ON b.patient_id = p.id ";

    public List<Bill> getAllBills(String username) throws SQLException {
        List<Bill> list = new ArrayList<>();
        String sql = SELECT_WITH_NAME + "WHERE p.username = ? ORDER BY b.id";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public List<Bill> getUnpaidBills(String username) throws SQLException {
        List<Bill> list = new ArrayList<>();
        String sql = SELECT_WITH_NAME + "WHERE b.paid = 0 AND p.username = ? ORDER BY b.id";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public boolean markAsPaid(int billId, String username) throws SQLException {
        String sql = "UPDATE bills SET paid = 1 WHERE id = ? AND patient_id IN (SELECT id FROM patients WHERE username = ?)";
        try (PreparedStatement stmt = Database.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, billId);
            stmt.setString(2, username);
            return stmt.executeUpdate() > 0;
        }
    }

    private Bill mapRow(ResultSet rs) throws SQLException {
        return new Bill(
                rs.getInt("id"),
                rs.getInt("patient_id"),
                rs.getString("patient_name"),
                rs.getString("description"),
                rs.getDouble("amount"),
                rs.getInt("paid") == 1,
                rs.getString("bill_date")
        );
    }
}
