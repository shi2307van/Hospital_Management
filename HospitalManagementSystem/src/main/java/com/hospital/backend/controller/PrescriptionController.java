package com.hospital.backend.controller;

import com.hospital.backend.entity.Prescription;
import com.hospital.backend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "*")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptionService.getAllPrescriptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable int id) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        return prescription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@RequestBody Prescription prescription) {
        return new ResponseEntity<>(prescriptionService.savePrescription(prescription), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable int id, @RequestBody Prescription prescription) {
        try {
            Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescription);
            return ResponseEntity.ok(updatedPrescription);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable int id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDoctor(@PathVariable int doctorId) {
        return ResponseEntity.ok(prescriptionService.findByDoctorId(doctorId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByPatient(@PathVariable int patientId) {
        return ResponseEntity.ok(prescriptionService.findByPatientId(patientId));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByAppointment(@PathVariable int appointmentId) {
        return ResponseEntity.ok(prescriptionService.findByAppointmentId(appointmentId));
    }

    @GetMapping("/patient/{patientId}/active")
    public ResponseEntity<List<Prescription>> getActivePrescriptions(@PathVariable int patientId) {
        return ResponseEntity.ok(prescriptionService.findActivePrescriptions(patientId));
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Prescription>> getPrescriptionsByDate(@PathVariable String date) {
        return ResponseEntity.ok(prescriptionService.findPrescriptionsByDate(date));
    }
}