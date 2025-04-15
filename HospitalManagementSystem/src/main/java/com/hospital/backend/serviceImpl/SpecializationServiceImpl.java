package com.hospital.backend.serviceImpl;

import com.hospital.backend.entity.Specialization;
import com.hospital.backend.repository.SpecializationRepository;
import com.hospital.backend.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecializationServiceImpl implements SpecializationService {

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public Specialization saveSpecialization(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public List<Specialization> getAllSpecializations() {
        return specializationRepository.findAll();
    }

    @Override
    public Optional<Specialization> getSpecializationById(int id) {
        return specializationRepository.findById(id);
    }

    @Override
    public Specialization updateSpecialization(int id, Specialization specialization) {
        if (specializationRepository.existsById(id)) {
            specialization.setSp_Id(id);
            return specializationRepository.save(specialization);
        }
        throw new RuntimeException("Specialization not found with id: " + id);
    }

    @Override
    public void deleteSpecialization(int id) {
        specializationRepository.deleteById(id);
    }

    @Override
    public Optional<Specialization> findByName(String name) {
        return specializationRepository.findByName(name);
    }

    @Override
    public Optional<Specialization> findById(int id) {
        return specializationRepository.findById(id);
    }


    @Override
    public boolean existsById(int id) {
        return specializationRepository.existsById(id);
    }
    
}