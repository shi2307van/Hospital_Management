package com.hospital.backend.repository;

import com.hospital.backend.entity.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    Optional<Doctor> findById(int id);
    Optional<Doctor> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByStatus(String status);
    List<Doctor> findByStatusAndSpecialization(String status, String specialization);
    List<Doctor> findByNameContainingIgnoreCase(String name);
    Doctor save(Doctor doctor);
    void deleteById(int id);
    List<Doctor> findAll();
    Doctor register(Doctor doctor);
    Doctor update(Doctor doctor);
    Doctor changeStatus(int id, String status);
    List<Doctor> getActiveDoctors();
    List<Doctor> getDoctorsBySpecialization(String specialization);
    boolean existsById(int id);
}