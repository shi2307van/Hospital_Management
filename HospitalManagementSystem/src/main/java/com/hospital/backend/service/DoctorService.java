package com.hospital.backend.service;

import com.hospital.backend.entity.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Doctor saveDoctor(Doctor doctor);
    List<Doctor> getAllDoctors();
    Optional<Doctor> getDoctorById(int id);
    Doctor updateDoctor(int id, Doctor doctor);
    void deleteDoctor(int id);
    Optional<Doctor> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByStatus(String status);
    Doctor updateDoctorProfile(int id, Doctor doctor);
    List<Doctor> findAvailableDoctors();
    boolean validateDoctorCredentials(String email, String password);
    boolean existsById(int id);
}