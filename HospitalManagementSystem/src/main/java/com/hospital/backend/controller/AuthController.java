package com.hospital.backend.controller;

import com.hospital.backend.entity.LoginRequest;
import com.hospital.backend.entity.LoginResponse;
import com.hospital.backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/doctor/login")
    public ResponseEntity<LoginResponse> doctorLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationService.authenticateDoctor(loginRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/patient/login")
    public ResponseEntity<LoginResponse> patientLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationService.authenticatePatient(loginRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationService.authenticateAdmin(loginRequest);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String token) {
        boolean isValid = authenticationService.validateToken(token);
        if (isValid) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(401).body(false);
        }
    }
}