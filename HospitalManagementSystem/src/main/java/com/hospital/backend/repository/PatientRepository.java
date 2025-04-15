package com.hospital.backend.repository;

import com.hospital.backend.entity.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Optional<Patient> findById(int id);
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Patient> findByBloodGroup(String bloodGroup);
    List<Patient> findByStatus(String status);
    List<Patient> findByNameContainingIgnoreCase(String name);
    List<Patient> findByContactContaining(String contact);
    Patient save(Patient patient);
    void deleteById(int id);
    List<Patient> findAll();
    Patient register(Patient patient);
    Patient update(Patient patient);
  
    List<Patient> getActivePatients();
    List<Patient> getPatientsByBloodGroup(String bloodGroup);
    Patient updateMedicalHistory(int id, String medicalHistory);
	boolean existsById(int id);
}