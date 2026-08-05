package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.BillDAO;
import com.hospitalmanagement.hospital.management.api.model.Bill;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillDAO billDAO = new BillDAO();

    @GetMapping
    public List<Bill> getAllBills() throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return billDAO.getAllBills(username);
    }

    @GetMapping("/unpaid")
    public List<Bill> getUnpaidBills() throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return billDAO.getUnpaidBills(username);
    }

    @PostMapping
    public String addBill(@RequestBody Bill bill) throws SQLException {
        int id = billDAO.addBill(
                bill.getPatientId(),
                bill.getDescription(),
                bill.getAmount(),
                bill.getBillDate()
        );
        return "Bill added successfully with ID: " + id;
    }

    @PutMapping("/{id}/pay")
    public String markAsPaid(@PathVariable int id) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean updated = billDAO.markAsPaid(id, username);
        return updated ? "Bill marked as paid." : "Bill not found.";
    }
}

