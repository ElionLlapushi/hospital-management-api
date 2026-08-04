package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.PatientDAO;
import com.hospitalmanagement.hospital.management.api.model.Patient;
import com.hospitalmanagement.hospital.management.api.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
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

    @GetMapping("/stats/today")
    public ResponseEntity<Integer> getTodayPatientsCount() throws SQLException {
        String today = LocalDate.now().toString();
        int count = patientDAO.getTodayPatientsCount(today);
        return ResponseEntity.ok(count);
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) throws SQLException {
        patientDAO.addPatient(patient);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @Valid @RequestBody Patient patientDetails) throws SQLException {
        Patient existingPatient = patientDAO.getPatientById(id);
        if (existingPatient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet për t'u përditësuar");
        }

        patientDetails.setId(id);
        patientDAO.updatePatient(patientDetails);

        return ResponseEntity.ok(patientDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) throws SQLException {
        Patient patient = patientDAO.getPatientById(id);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet për t'u fshirë");
        }

        patientDAO.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}