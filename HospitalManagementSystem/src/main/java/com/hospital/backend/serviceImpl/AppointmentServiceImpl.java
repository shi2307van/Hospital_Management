package com.hospital.backend.serviceImpl;

import com.hospital.backend.entity.Appointment;
import com.hospital.backend.repository.AppointmentRepository;
import com.hospital.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(int id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        if (appointmentRepository.existsById(id)) {
            appointment.setAp_ID(id);
            return appointmentRepository.save(appointment);
        }
        throw new RuntimeException("Appointment not found with id: " + id);
    }

    @Override
    public void deleteAppointment(int id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findByDoctorId(int doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> findByPatientId(int patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> findByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    @Override
    public List<Appointment> findByDate(String date) {
        return appointmentRepository.findByDate(date);
    }

    @Override
    public List<Appointment> findUpcomingAppointments(int doctorId) {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        return appointmentRepository.findByDoctorIdAndDateGreaterThanEqualAndStatus(doctorId, today, "Scheduled");
    }

    @Override
    public List<Appointment> findPastAppointments(int doctorId) {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        return appointmentRepository.findByDoctorIdAndDateLessThan(doctorId, today);
    }

    @Override
    public List<Appointment> findTodayAppointments(int doctorId) {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        return appointmentRepository.findByDoctorIdAndDate(doctorId, today);
    }
}