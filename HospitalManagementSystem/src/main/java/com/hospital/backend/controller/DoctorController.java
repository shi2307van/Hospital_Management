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

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        return new ResponseEntity<>(doctorService.saveDoctor(doctor), HttpStatus.CREATED);
    }
    
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


    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable int id, @RequestBody Doctor doctor) {
        doctor.setDrId(id); // Set ID from path variable
        
        if (!doctorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        Doctor updatedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(doctorService.getDoctorsBySpecialization(specialization));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Doctor>> searchDoctorsByName(@RequestParam String name) {
        return ResponseEntity.ok(doctorService.searchDoctorsByName(name));
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Doctor>> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(doctorService.findByEmail(email));
    }

}