package com.hospital.backend.controller;

import com.hospital.backend.entity.Specialization;
import com.hospital.backend.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/specializations")
@CrossOrigin(origins = "*")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    @GetMapping
    public ResponseEntity<List<Specialization>> getAllSpecializations() {
        return ResponseEntity.ok(specializationService.getAllSpecializations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Specialization> getSpecializationById(@PathVariable int id) {
        Optional<Specialization> specialization = specializationService.findById(id);
        return specialization.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Specialization> createSpecialization(@RequestBody Specialization specialization) {
        return new ResponseEntity<>(specializationService.saveSpecialization(specialization), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Specialization> updateSpecialization(@PathVariable int id, @RequestBody Specialization specialization) {
        try {
            Specialization updatedSpecialization = specializationService.updateSpecialization(id, specialization);
            return ResponseEntity.ok(updatedSpecialization);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable int id) {
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Specialization> getSpecializationByName(@PathVariable String name) {
        Optional<Specialization> specialization = specializationService.findByName(name);
        return specialization.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Specialization>> getSpecializationsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(specializationService.findByStatus(status));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Specialization>> searchSpecializations(@RequestParam String name) {
        return ResponseEntity.ok(specializationService.findByNameContaining(name));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkSpecializationExists(@RequestParam String name) {
        return ResponseEntity.ok(specializationService.existsByName(name));
    }
}