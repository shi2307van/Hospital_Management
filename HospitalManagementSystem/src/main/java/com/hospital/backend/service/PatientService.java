package com.hospital.backend.service;

import com.hospital.backend.entity.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient savePatient(Patient patient);
    List<Patient> getAllPatients();
    Optional<Patient> getPatientById(int id);
    Patient updatePatient(int id, Patient patient);
    void deletePatient(int id);
    Optional<Patient> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Patient> findByBloodGroup(String bloodGroup);
    Patient updatePatientProfile(int id, Patient patient);
    boolean validatePatientCredentials(String email, String password);
    List<Patient> findActivePatients();
    boolean existsById(int id);
}