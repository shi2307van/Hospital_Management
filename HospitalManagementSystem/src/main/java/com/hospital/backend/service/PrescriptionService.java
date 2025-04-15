package com.hospital.backend.service;

import com.hospital.backend.entity.Prescription;
import java.util.List;
import java.util.Optional;

public interface PrescriptionService {
    Prescription savePrescription(Prescription prescription);
    List<Prescription> getAllPrescriptions();
    Optional<Prescription> getPrescriptionById(int id);
    Prescription updatePrescription(int id, Prescription prescription);
    void deletePrescription(int id);
    List<Prescription> findByDoctorId(int doctorId);
    List<Prescription> findByPatientId(int patientId);
    List<Prescription> findByAppointmentId(int appointmentId);
    List<Prescription> findActivePrescriptions(int patientId);
    List<Prescription> findPrescriptionsByDate(String date);
    Optional<Prescription> findById(int id);
	boolean existsById(int id);
}