package com.hospital.backend.controller;

import com.hospital.backend.entity.Appointment;
import com.hospital.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        return appointment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.saveAppointment(appointment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointment);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable int doctorId) {
        return ResponseEntity.ok(appointmentService.findByDoctorId(doctorId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable int patientId) {
        return ResponseEntity.ok(appointmentService.findByPatientId(patientId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(appointmentService.findByStatus(status));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.findByDate(date.toString()));
    }

    @GetMapping("/doctor/{doctorId}/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctorAndDate(
            @PathVariable int doctorId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.findByDoctorIdAndDate(doctorId, date.toString()));
    }

    @GetMapping("/patient/{patientId}/date/{date}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientAndDate(
            @PathVariable int patientId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.findByPatientIdAndDate(patientId, date.toString()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(
            @PathVariable int id, 
            @RequestParam String status) {
        return ResponseEntity.ok(appointmentService.updateStatus(id, status));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkAppointmentAvailability(
            @RequestParam int doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam String time) {
        boolean isAvailable = appointmentService.isTimeSlotAvailable(doctorId, date.toString(), time);
        return ResponseEntity.ok(isAvailable);
    }
}