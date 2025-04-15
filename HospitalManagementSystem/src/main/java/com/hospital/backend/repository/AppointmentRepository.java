package com.hospital.backend.repository;

import com.hospital.backend.entity.Appointment;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    Optional<Appointment> findById(int id);
    boolean existsById(int id);
    List<Appointment> findByDoctorId(int doctorId);
    List<Appointment> findByPatientId(int patientId);
    List<Appointment> findByStatus(String status);
    List<Appointment> findByDate(String date);
    List<Appointment> findByDoctorIdAndDate(int doctorId, String date);
    List<Appointment> findByDoctorIdAndStatus(int doctorId, String status);
    List<Appointment> findByPatientIdAndStatus(int patientId, String status);
    List<Appointment> findByDoctorIdAndDateGreaterThanEqual(int doctorId, String date);
    List<Appointment> findByDoctorIdAndDateLessThan(int doctorId, String date);
    List<Appointment> findByDoctorIdAndDateGreaterThanEqualAndStatus(int doctorId, String date, String status);
    boolean existsByDoctorIdAndDateAndTime(int doctorId, String date, Time time);
    Appointment save(Appointment appointment);
    void deleteById(int id);
    List<Appointment> findAll();
    Appointment createAppointment(Appointment appointment);
    Appointment updateAppointment(Appointment appointment);
    Appointment changeAppointmentStatus(int id, String status);
    List<Appointment> getUpcomingAppointments(int doctorId);
    List<Appointment> getPastAppointments(int doctorId);
    List<Appointment> getPatientAppointments(int patientId);
    boolean isTimeSlotAvailable(int doctorId, String date, String time);
	
}