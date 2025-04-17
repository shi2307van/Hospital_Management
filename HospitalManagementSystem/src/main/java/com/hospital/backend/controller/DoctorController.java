package com.hospital.backend.controller;

import com.hospital.backend.entity.Doctor;
import com.hospital.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        return doctor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.saveDoctor(doctor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable int id, @RequestBody Doctor doctor) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
            return ResponseEntity.ok(updatedDoctor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(doctorService.findBySpecialization(specialization));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctorsByName(@RequestParam String name) {
        return ResponseEntity.ok(doctorService.findByNameContainingIgnoreCase(name));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Doctor>> getDoctorsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(doctorService.findByStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Doctor> updateDoctorStatus(@PathVariable int id, @RequestParam String status) {
        return ResponseEntity.ok(doctorService.updateDoctorStatus(id, status));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<Doctor> updateDoctorProfile(@PathVariable int id, @RequestBody Doctor doctor) {
        return ResponseEntity.ok(doctorService.updateDoctorProfile(id, doctor));
    }
}