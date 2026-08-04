package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.DoctorDAO;
import com.hospitalmanagement.hospital.management.api.model.Doctor;
import com.hospitalmanagement.hospital.management.api.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorDAO doctorDAO = new DoctorDAO();

    @GetMapping
    public List<Doctor> getAllDoctors() throws SQLException {
        return doctorDAO.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable int id) throws SQLException {
        Doctor doctor = doctorDAO.getDoctorById(id);
        if (doctor == null) {
            throw new ResourceNotFoundException("Mjeku me ID " + id + " nuk u gjet");
        }
        return doctor;
    }

    @PostMapping
    public String addDoctor(@RequestBody Doctor doctor) throws SQLException {
        int id = doctorDAO.addDoctor(
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getPhone()
        );
        return "Doctor added with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable int id) throws SQLException {
        boolean deleted = doctorDAO.deleteDoctor(id);
        return deleted ? "Doctor deleted successfully." : "Doctor not found.";
    }
}
