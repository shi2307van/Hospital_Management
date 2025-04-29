package com.hospital.backend.serviceImpl;

import com.hospital.backend.entity.LoginRequest;
import com.hospital.backend.entity.LoginResponse;
import com.hospital.backend.entity.Doctor;
import com.hospital.backend.entity.Patient;
import com.hospital.backend.service.AuthenticationService;
import com.hospital.backend.service.DoctorService;
import com.hospital.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Map<String, String> tokenUserMap = new HashMap<>();
    private final String ADMIN_EMAIL = "admin@hospital.com";
    private final String ADMIN_PASSWORD = "admin123";

    @Override
    public LoginResponse authenticateDoctor(LoginRequest loginRequest) {
        Optional<Doctor> doctor = doctorService.findByEmail(loginRequest.getEmail());
        
        if (doctor.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), doctor.get().getPassword())) {
            String token = generateToken();
            tokenUserMap.put(token, "DOCTOR_" + doctor.get().getDrId());
            
            return new LoginResponse(
                token,
                "DOCTOR",
                doctor.get().getDrId(),
                doctor.get().getDrName(),
                doctor.get().getEmailId(),
                "Login successful",
                true
            );
        }
        
        return new LoginResponse(null, "DOCTOR", 0, null, null, "Invalid credentials", false);
    }

    @Override
    public LoginResponse authenticatePatient(LoginRequest loginRequest) {
        Optional<Patient> patient = patientService.findByEmail(loginRequest.getEmail());
        
        if (patient.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), patient.get().getPassword())) {
            String token = generateToken();
            tokenUserMap.put(token, "PATIENT_" + patient.get().getpId());
            
            return new LoginResponse(
                token,
                "PATIENT",
                patient.get().getpId(),
                patient.get().getName(),
                patient.get().getEmail(),
                "Login successful",
                true
            );
        }
        
        return new LoginResponse(null, "PATIENT", 0, null, null, "Invalid credentials", false);
    }

    @Override
    public LoginResponse authenticateAdmin(LoginRequest loginRequest) {
        if (loginRequest.getEmail().equals(ADMIN_EMAIL) && 
            loginRequest.getPassword().equals(ADMIN_PASSWORD)) {
            String token = generateToken();
            tokenUserMap.put(token, "ADMIN");
            
            return new LoginResponse(
                token,
                "ADMIN",
                0,
                "Admin",
                ADMIN_EMAIL,
                "Login successful",
                true
            );
        }
        
        return new LoginResponse(null, "ADMIN", 0, null, null, "Invalid credentials", false);
    }

    @Override
    public void logout(String token) {
        tokenUserMap.remove(token);
    }

    @Override
    public boolean validateToken(String token) {
        return tokenUserMap.containsKey(token);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}