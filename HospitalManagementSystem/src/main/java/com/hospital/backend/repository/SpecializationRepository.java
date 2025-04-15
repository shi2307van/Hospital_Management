package com.hospital.backend.repository;

import com.hospital.backend.entity.Specialization;
import java.util.List;
import java.util.Optional;

public interface SpecializationRepository {
    Optional<Specialization> findByName(String name);
    List<Specialization> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    Specialization save(Specialization specialization);
    void deleteById(int id);
    List<Specialization> findAll();
    Specialization createSpecialization(Specialization specialization);
    Specialization updateSpecialization(Specialization specialization);
    List<Specialization> getSpecializationsByName(String name);
    boolean existsById(int id);
    Optional<Specialization> findById(int id);
}