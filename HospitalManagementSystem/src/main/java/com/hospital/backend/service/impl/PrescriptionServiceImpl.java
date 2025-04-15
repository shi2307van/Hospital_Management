package com.hospital.backend.service.impl;

import com.hospital.backend.entity.Prescription;
import com.hospital.backend.repository.PrescriptionRepository;
import com.hospital.backend.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Optional<Prescription> getPrescriptionById(int id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public Prescription updatePrescription(int id, Prescription prescription) {
        if (prescriptionRepository.existsById(id)) {
            prescription.setId(id);
            return prescriptionRepository.save(prescription);
        }
        throw new RuntimeException("Prescription not found with id: " + id);
    }

    @Override
    public void deletePrescription(int id) {
        prescriptionRepository.deleteById(id);
    }

    @Override
    public List<Prescription> findByDoctorId(int doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<Prescription> findByPatientId(int patientId) {
        return prescriptionRepository.findByPatientId(patientId);
    }

    @Override
    public List<Prescription> findByAppointmentId(int appointmentId) {
        return prescriptionRepository.findByAppointmentId(appointmentId);
    }

    @Override
    public List<Prescription> findActivePrescriptions(int patientId) {
        return prescriptionRepository.findByPatientIdAndStatus(patientId, "Active");
    }

    @Override
    public List<Prescription> findPrescriptionsByDate(String date) {
        return prescriptionRepository.findByDate(date);
    }
}