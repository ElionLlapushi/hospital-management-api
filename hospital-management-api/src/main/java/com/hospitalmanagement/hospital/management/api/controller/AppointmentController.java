package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.AppointmentDAO;
import com.hospitalmanagement.hospital.management.api.dao.PatientDAO;
import com.hospitalmanagement.hospital.management.api.model.Appointment;
import com.hospitalmanagement.hospital.management.api.model.Patient;
import com.hospitalmanagement.hospital.management.api.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final PatientDAO patientDAO = new PatientDAO();

    // Metodë ndihmëse për të marrë clinicId nga detajet e tokenit JWT
    private int getCurrentClinicId() {
        try {
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof Integer) {
                return (Integer) details;
            }
        } catch (Exception e) {
            // Ignoron gabimin
        }
        return 1; // Vlera parazgjedhje
    }

    @GetMapping
    public List<Appointment> getAllAppointments() throws SQLException {
        int clinicId = getCurrentClinicId();
        return appointmentDAO.getAllAppointments(clinicId);
    }

    @GetMapping("/doctor/{doctorId}/availability")
    public List<Appointment> getDoctorAvailability(@PathVariable int doctorId, @RequestParam String date) throws SQLException {
        int clinicId = getCurrentClinicId();
        return appointmentDAO.getAppointmentsByDoctorAndDate(doctorId, date, clinicId);
    }

    @PostMapping
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody Appointment appointment) throws SQLException {
        int clinicId = getCurrentClinicId();

        // Verifikojmë që pacienti ekziston dhe i përket klinikës aktuale
        Patient patient = patientDAO.getPatientByIdAndClinicId(appointment.getPatientId(), clinicId);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + appointment.getPatientId() + " nuk u gjet ose nuk i përket klinikës tuaj");
        }

        int id = appointmentDAO.bookAppointment(
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDate(),
                clinicId
        );

        if (id != -1) {
            return new ResponseEntity<>("Appointment booked successfully with ID: " + id, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to book appointment");
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateAppointmentStatus(@PathVariable int id, @RequestParam String status) throws SQLException {
        int clinicId = getCurrentClinicId();
        boolean updated = appointmentDAO.updateStatus(id, status, clinicId);
        if (updated) {
            return ResponseEntity.ok("Appointment status updated successfully");
        }
        throw new ResourceNotFoundException("Takimi me ID " + id + " nuk u gjet");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable int id) throws SQLException {
        int clinicId = getCurrentClinicId();
        boolean cancelled = appointmentDAO.cancelAppointment(id, clinicId);
        if (cancelled) {
            return ResponseEntity.ok("Appointment cancelled successfully");
        }
        throw new ResourceNotFoundException("Takimi me ID " + id + " nuk u gjet");
    }
}