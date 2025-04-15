package com.hospital.backend.serviceImpl;

import com.hospital.backend.entity.Patient;
import com.hospital.backend.repository.PatientRepository;
import com.hospital.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Patient savePatient(Patient patient) {
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(int id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient updatePatient(int id, Patient patient) {
        if (patientRepository.existsById(id)) {
            patient.setpId(id);
            return patientRepository.save(patient);
        }
        throw new RuntimeException("Patient not found with id: " + id);
    }

    @Override
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return patientRepository.existsByEmail(email);
    }

    @Override
    public List<Patient> findByBloodGroup(String bloodGroup) {
        return patientRepository.findByBloodGroup(bloodGroup);
    }

    @Override
    public Patient updatePatientProfile(int id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        
        existingPatient.setpId(patient.getpId());
        existingPatient.setName(patient.getName());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setBloodGroup(patient.getBloodGroup());
        existingPatient.setAge(patient.getAge());
        existingPatient.setDob(patient.getDob());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setGender(patient.getGender());
        existingPatient.setMobileNo(patient.getMobileNo());
        existingPatient.setPassword(patient.getPassword());
        
        return patientRepository.save(existingPatient);
    }

    @Override
    public boolean validatePatientCredentials(String email, String password) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        return patient.isPresent() && passwordEncoder.matches(password, patient.get().getPassword());
    }

    @Override
    public List<Patient> findActivePatients() {
        return patientRepository.findByStatus("Active");
    }
    @Override
    public boolean existsById(int id) {
        return patientRepository.existsById(id);
    }
} 