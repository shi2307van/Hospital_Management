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
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.createAppointment(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }

    @Override
    public Optional<Appointment> getAppointmentById(int id) {
        return appointmentRepository.getAppointmentById(id);
    }

    @Override
    public Appointment updateAppointment(int id, Appointment appointment) {
        if (appointmentRepository.existsById(id)) {
            appointment.setApId(id);
            return appointmentRepository.updateAppointment(appointment);
        }
        throw new RuntimeException("Appointment not found with id: " + id);
    }

    @Override
    public void deleteAppointment(int id) {
        appointmentRepository.deleteAppointment(id);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return appointmentRepository.getAppointmentsByDoctor(doctorId);
    }

    @Override
    public List<Appointment> getAppointmentsByPatient(int patientId) {
        return appointmentRepository.getAppointmentsByPatient(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.getAppointmentsByStatus(status);
    }

    @Override
    public List<Appointment> getAppointmentsByDate(String date) {
        return appointmentRepository.getAppointmentsByDate(date);
    }

    @Override
    public List<Appointment> getUpcomingAppointments(int doctorId) {
        return appointmentRepository.getUpcomingAppointments(doctorId);
    }

    @Override
    public List<Appointment> getPastAppointments(int doctorId) {
        return appointmentRepository.getPastAppointments(doctorId);
    }

    @Override
    public List<Appointment> getTodayAppointments(int doctorId) {
        return appointmentRepository.getTodayAppointments(doctorId);
    }

	@Override
	public Appointment updateStatus(int id, String status) {
		return appointmentRepository.updateStatus(id, status);
	}
}