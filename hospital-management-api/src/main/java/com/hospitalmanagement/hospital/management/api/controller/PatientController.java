package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.PatientDAO;
import com.hospitalmanagement.hospital.management.api.dao.WhatsAppService;
import com.hospitalmanagement.hospital.management.api.model.Patient;
import com.hospitalmanagement.hospital.management.api.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientDAO patientDAO = new PatientDAO();
    private final WhatsAppService whatsAppService = new WhatsAppService();

    @GetMapping
    public List<Patient> getAllPatients() throws SQLException {
        // Marrim username-in e përdoruesit të loguar nga token-i
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return patientDAO.getPatientsByUsername(username);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable int id) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientDAO.getPatientByIdAndUsername(id, username);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet");
        }
        return patient;
    }

    @GetMapping("/stats/today")
    public ResponseEntity<Integer> getTodayPatientsCount() throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String today = LocalDate.now().toString();
        int count = patientDAO.getTodayPatientsCountByUsername(today, username);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}/whatsapp-link")
    public ResponseEntity<String> getWhatsAppLink(@PathVariable int id) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientDAO.getPatientByIdAndUsername(id, username);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet");
        }

        String link = whatsAppService.generateWhatsAppLink(patient.getPhone(), patient.getName(), patient.getAdmitDate());
        return ResponseEntity.ok(link);
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) throws SQLException {
        // Vendosim automatikisht username-in e përdoruesit të loguar para se ta ruajmë
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        patient.setUsername(username);

        patientDAO.addPatient(patient);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @Valid @RequestBody Patient patientDetails) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient existingPatient = patientDAO.getPatientByIdAndUsername(id, username);
        if (existingPatient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet për t'u përditësuar");
        }

        patientDetails.setId(id);
        patientDetails.setUsername(username);
        patientDAO.updatePatient(patientDetails);

        return ResponseEntity.ok(patientDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Patient patient = patientDAO.getPatientByIdAndUsername(id, username);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet për t'u fshirë");
        }

        patientDAO.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}