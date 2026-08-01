package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.PatientDAO;
import com.hospitalmanagement.hospital.management.api.model.Patient;
import com.hospitalmanagement.hospital.management.api.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientDAO patientDAO = new PatientDAO();

    @GetMapping
    public List<Patient> getAllPatients() throws SQLException {
        return patientDAO.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable int id) throws SQLException {
        Patient patient = patientDAO.getPatientById(id);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet");
        }
        return patient;
    }

    @PostMapping
    public String addPatient(@RequestBody Patient patient) throws SQLException {
        int id = patientDAO.addPatient(
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getPhone(),
                patient.getAddress(),
                patient.getDisease(),
                patient.getAdmitDate()
        );
        return "Patient added with ID: " + id;
    }

    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable int id) throws SQLException {
        boolean deleted = patientDAO.deletePatient(id);
        return deleted ? "Deleted." : "Patient not found.";
    }
}