package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.AppointmentDAO;
import com.hospitalmanagement.hospital.management.api.model.Appointment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

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
    public String bookAppointment(@RequestBody Appointment appointment) throws SQLException {
        int id = appointmentDAO.bookAppointment(
                appointment.getPatientId(),
                appointment.getDoctorId(),
                appointment.getAppointmentDate()
        );
        return "Appointment booked successfully with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String cancelAppointment(@PathVariable int id) throws SQLException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean cancelled = appointmentDAO.cancelAppointment(id, username);
        return cancelled ? "Appointment cancelled." : "Appointment not found.";
    }
}
