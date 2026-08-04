package com.hospitalmanagement.hospital.management.api.controller;

import com.hospitalmanagement.hospital.management.api.dao.AppointmentDAO;
import com.hospitalmanagement.hospital.management.api.model.Appointment;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    @GetMapping
    public List<Appointment> getAllAppointments() throws SQLException {
        return appointmentDAO.getAllAppointments();
    }

    // Endpoint-i i ri për të kontrolluar oraret/rezervimet e mjekut në një datë të caktuar
    @GetMapping("/doctor/{doctorId}/availability")
    public List<Appointment> getDoctorAvailability(@PathVariable int doctorId, @RequestParam String date) throws SQLException {
        return appointmentDAO.getAppointmentsByDoctorAndDate(doctorId, date);
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
        boolean cancelled = appointmentDAO.cancelAppointment(id);
        return cancelled ? "Appointment cancelled." : "Appointment not found.";
    }
}
