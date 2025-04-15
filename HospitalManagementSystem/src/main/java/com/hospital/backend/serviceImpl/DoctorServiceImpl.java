package com.hospital.backend.serviceImpl;

import com.hospital.backend.entity.Doctor;
import com.hospital.backend.repository.DoctorRepository;
import com.hospital.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getDoctorById(int id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor updateDoctor(int id, Doctor doctor) {
        if (doctorRepository.existsById(id)) {
            doctor.setDR_ID(id);
            return doctorRepository.save(doctor);
        }
        throw new RuntimeException("Doctor not found with id: " + id);
    }

    @Override
    public void deleteDoctor(int id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }

    @Override
    public List<Doctor> findBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    @Override
    public List<Doctor> findByStatus(String status) {
        return doctorRepository.findByStatus(status);
    }



    @Override
    public Doctor updateDoctorProfile(int id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        
        
        existingDoctor.setDr_name(doctor.getDr_name());
        existingDoctor.setSp_Id(doctor.getSp_Id());
        existingDoctor.setMobile_no(doctor.getMobile_no());
        existingDoctor.setAge(doctor.getAge());
        existingDoctor.setExperience(doctor.getExperience());
        existingDoctor.setDR_ID(doctor.getDR_ID());
        existingDoctor.setEmail_id(doctor.getEmail_id());
        existingDoctor.setGender(doctor.getGender());
        existingDoctor.setPassword(doctor.getPassword());
        existingDoctor.setPicture(doctor.getPicture());
   
        
        return doctorRepository.save(existingDoctor);
    }

    @Override
    public List<Doctor> findAvailableDoctors() {
        return doctorRepository.findByStatus("Available");
    }

    @Override
    public boolean validateDoctorCredentials(String email, String password) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        return doctor.isPresent() && passwordEncoder.matches(password, doctor.get().getPassword());
    }

    @Override
    public boolean existsById(int id) {
        return doctorRepository.existsById(id);
    }
}