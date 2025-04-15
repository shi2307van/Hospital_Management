package com.hospital.backend.repository;

import com.hospital.backend.entity.Prescription;
import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository {
    List<Prescription> findByDoctorId(int doctorId);
    List<Prescription> findByPatientId(int patientId);
    List<Prescription> findByAppointmentId(int appointmentId);
    List<Prescription> findByPatientIdAndStatus(int patientId, String status);
    List<Prescription> findByDate(String date);
    List<Prescription> findByDoctorIdAndDate(int doctorId, String date);
    List<Prescription> findByPatientIdAndDate(int patientId, String date);
    Prescription save(Prescription prescription);
    void deleteById(int id);
    List<Prescription> findAll();
    Prescription createPrescription(Prescription prescription);
    Prescription updatePrescription(Prescription prescription);
    Prescription changePrescriptionStatus(int id, String status);
    List<Prescription> getPatientPrescriptions(int patientId);
    List<Prescription> getDoctorPrescriptions(int doctorId);
    List<Prescription> getPrescriptionsByAppointment(int appointmentId);
    Prescription getPrescriptionById(int id);
	Optional<Prescription> findById(int id);
	boolean existsById(int id);
}