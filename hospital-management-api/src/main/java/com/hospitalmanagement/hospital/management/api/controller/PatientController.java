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

    // Metodë ndihmëse për të marrë clinicId (mund ta përshtatësh sipas mënyrës se si e ruan te JWT token)
    private int getCurrentClinicId() {
        // Për momentin e marrim si integer nga detajet e autentifikimit ose e përshtati sipas logjikës sate të tokenit
        // P.sh: return (int) SecurityContextHolder.getContext().getAuthentication().getDetails();
        // Ose nëse e ruani te Credentials/Principal:
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            // Nëse ruhet si ID direkte ose string, e konvertojmë. Këtu po vendosim një shembull standard:
            return Integer.parseInt(principal.toString());
        } catch (Exception e) {
            return 1; // Vlera e paracaktuar për testim nëse nuk është mapuar ende plotësisht në token
        }
    }

    @GetMapping
    public List<Patient> getAllPatients() throws SQLException {
        int clinicId = getCurrentClinicId();
        return patientDAO.getPatientsByClinicId(clinicId);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable int id) throws SQLException {
        int clinicId = getCurrentClinicId();
        Patient patient = patientDAO.getPatientByIdAndClinicId(id, clinicId);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet");
        }
        return patient;
    }

    @GetMapping("/stats/today")
    public ResponseEntity<Integer> getTodayPatientsCount() throws SQLException {
        int clinicId = getCurrentClinicId();
        String today = LocalDate.now().toString();
        int count = patientDAO.getTodayPatientsCountByClinicId(today, clinicId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{id}/whatsapp-link")
    public ResponseEntity<String> getWhatsAppLink(@PathVariable int id) throws SQLException {
        int clinicId = getCurrentClinicId();
        Patient patient = patientDAO.getPatientByIdAndClinicId(id, clinicId);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet");
        }

        String link = whatsAppService.generateWhatsAppLink(patient.getPhone(), patient.getName(), patient.getAdmitDate());
        return ResponseEntity.ok(link);
    }

    @PostMapping
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient patient) throws SQLException {
        int clinicId = getCurrentClinicId();
        patient.setClinicId(clinicId);

        patientDAO.addPatient(patient);
        return new ResponseEntity<>(patient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @Valid @RequestBody Patient patientDetails) throws SQLException {
        int clinicId = getCurrentClinicId();
        Patient existingPatient = patientDAO.getPatientByIdAndClinicId(id, clinicId);
        if (existingPatient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet për t'u përditësuar");
        }

        patientDetails.setId(id);
        patientDetails.setClinicId(clinicId);
        patientDAO.updatePatient(patientDetails);

        return ResponseEntity.ok(patientDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable int id) throws SQLException {
        int clinicId = getCurrentClinicId();
        Patient patient = patientDAO.getPatientByIdAndClinicId(id, clinicId);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + id + " nuk u gjet për t'u fshirë");
        }

        patientDAO.deletePatient(id, clinicId);
        return ResponseEntity.noContent().build();
    }
}