package com.hospital.backend.service;

import com.hospital.backend.entity.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment saveAppointment(Appointment appointment);
    List<Appointment> getAllAppointments();
    Optional<Appointment> getAppointmentById(int id);
    Appointment updateAppointment(int id, Appointment appointment);
    void deleteAppointment(int id);
    List<Appointment> findByDoctorId(int doctorId);
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByStatus(String status);
    List<Appointment> findByDate(String date);
    List<Appointment> findUpcomingAppointments(int doctorId);
    List<Appointment> findPastAppointments(int doctorId);
    List<Appointment> findTodayAppointments(int doctorId);
}