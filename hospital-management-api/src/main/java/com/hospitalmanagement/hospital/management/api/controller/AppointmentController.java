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

    @GetMapping
    public List<Appointment> getAllAppointments() throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return appointmentDAO.getAllAppointments(username);
    }

    @GetMapping("/doctor/{doctorId}/availability")
    public List<Appointment> getDoctorAvailability(@PathVariable int doctorId, @RequestParam String date) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return appointmentDAO.getAppointmentsByDoctorAndDate(doctorId, date, username);
    }

    @PostMapping
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody Appointment appointment) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Verifikojmë që pacienti ekziston dhe i përket përdoruesit të loguar
        Patient patient = patientDAO.getPatientByIdAndUsername(appointment.getPatientId(), username);
        if (patient == null) {
            throw new ResourceNotFoundException("Pacienti me ID " + appointment.getPatientId() + " nuk u gjet ose nuk ju përket juve");
        }

        int id = appointmentDAO.bookAppointment(
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDate()
        );

        if (id != -1) {
            return new ResponseEntity<>("Appointment booked successfully with ID: " + id, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to book appointment");
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateAppointmentStatus(@PathVariable int id, @RequestParam String status) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean updated = appointmentDAO.updateStatus(id, status, username);
        if (updated) {
            return ResponseEntity.ok("Appointment status updated successfully");
        }
        throw new ResourceNotFoundException("Takimi me ID " + id + " nuk u gjet");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable int id) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean cancelled = appointmentDAO.cancelAppointment(id, username);
        if (cancelled) {
            return ResponseEntity.ok("Appointment cancelled successfully");
        }
        throw new ResourceNotFoundException("Takimi me ID " + id + " nuk u gjet");
    }
}