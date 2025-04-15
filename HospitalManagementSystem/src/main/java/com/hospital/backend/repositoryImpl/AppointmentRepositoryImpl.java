package com.hospital.backend.repositoryImpl;

import com.hospital.backend.entity.Appointment;
import com.hospital.backend.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.sql.Time;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Appointment> appointmentRowMapper = new RowMapper<Appointment>() {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setAp_ID(rs.getInt("Ap_ID"));
            appointment.setP_ID(rs.getInt("P_ID"));
            appointment.setDR_ID(rs.getInt("D_ID"));
            appointment.setDescript(rs.getString("Descript"));
            appointment.setCancel_confirm(rs.getInt("cancel_confirm"));
            appointment.setAppointment_date(rs.getDate("appointment_date"));
            appointment.setAppointment_time(rs.getTime("appointment_time"));
            appointment.setStatus(rs.getString("status"));
            return appointment;
        }
    };

    @Override
    public Optional<Appointment> findById(int id) {
        String sql = "SELECT * FROM appointments WHERE Ap_ID = ?";
        List<Appointment> appointments = jdbcTemplate.query(sql, appointmentRowMapper, id);
        return appointments.isEmpty() ? Optional.empty() : Optional.of(appointments.get(0));
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE Ap_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<Appointment> findByDoctorId(int doctorId) {
        String sql = "SELECT * FROM appointments WHERE D_ID = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, doctorId);
    }

    @Override
    public List<Appointment> findByPatientId(int patientId) {
        String sql = "SELECT * FROM appointments WHERE P_ID = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, patientId);
    }

    @Override
    public List<Appointment> findByStatus(String status) {
        String sql = "SELECT * FROM appointments WHERE status = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, status);
    }

    @Override
    public List<Appointment> findByDate(String date) {
        String sql = "SELECT * FROM appointments WHERE appointment_date = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, date);
    }

    @Override
    public List<Appointment> findByDoctorIdAndDate(int doctorId, String date) {
        String sql = "SELECT * FROM appointments WHERE D_ID = ? AND appointment_date = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, doctorId, date);
    }

    @Override
    public List<Appointment> findByDoctorIdAndStatus(int doctorId, String status) {
        String sql = "SELECT * FROM appointments WHERE D_ID = ? AND status = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, doctorId, status);
    }

    @Override
    public List<Appointment> findByPatientIdAndStatus(int patientId, String status) {
        String sql = "SELECT * FROM appointments WHERE P_ID = ? AND status = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, patientId, status);
    }

    @Override
    public List<Appointment> findByDoctorIdAndDateGreaterThanEqual(int doctorId, String date) {
        String sql = "SELECT * FROM appointments WHERE D_ID = ? AND appointment_date >= ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, doctorId, date);
    }

    @Override
    public List<Appointment> findByDoctorIdAndDateLessThan(int doctorId, String date) {
        String sql = "SELECT * FROM appointments WHERE D_ID = ? AND appointment_date < ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, doctorId, date);
    }

    @Override
    public List<Appointment> findByDoctorIdAndDateGreaterThanEqualAndStatus(int doctorId, String date, String status) {
        String sql = "SELECT * FROM appointments WHERE D_ID = ? AND appointment_date >= ? AND status = ?";
        return jdbcTemplate.query(sql, appointmentRowMapper, doctorId, date, status);
    }

    @Override
    public boolean existsByDoctorIdAndDateAndTime(int doctorId, String date, Time time) {
    	 String sql = "SELECT COUNT(*) FROM appointments WHERE D_ID = ? AND appointment_date = ? AND appointment_time = ?";
	        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, doctorId, date, time);
	        return count != null && count > 0;
    }

    @Override
    public Appointment save(Appointment appointment) {
        if (appointment.getAp_ID() == 0) {
            String sql = "INSERT INTO appointments (P_ID, D_ID, Descript, cancel_confirm, appointment_date, appointment_time, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, 
                appointment.getP_ID(),
                appointment.getDR_ID(),
                appointment.getDescript(),
                appointment.getCancel_confirm(),
                appointment.getAppointment_date(),
                appointment.getAppointment_time(),
                appointment.getStatus());
            return appointment;
        } else {
            String sql = "UPDATE appointments SET P_ID = ?, D_ID = ?, Descript = ?, " +
                        "cancel_confirm = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE Ap_ID = ?";
            jdbcTemplate.update(sql,
                appointment.getP_ID(),
                appointment.getDR_ID(),
                appointment.getDescript(),
                appointment.getCancel_confirm(),
                appointment.getAppointment_date(),
                appointment.getAppointment_time(),
                appointment.getStatus(),
                appointment.getAp_ID());
            return appointment;
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM appointments WHERE Ap_ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Appointment> findAll() {
        String sql = "SELECT * FROM appointments";
        return jdbcTemplate.query(sql, appointmentRowMapper);
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return save(appointment);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        if (appointment.getAp_ID() == 0) {
            throw new IllegalArgumentException("Appointment ID must be provided for update");
        }
        return save(appointment);
    }

    @Override
    public Appointment changeAppointmentStatus(int id, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE Ap_ID = ?";
        jdbcTemplate.update(sql, status, id);
        
        return findById(id).orElse(null);
    }

    @Override
    public List<Appointment> getUpcomingAppointments(int doctorId) {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        String date = currentDate.toString();
        return findByDoctorIdAndDateGreaterThanEqual(doctorId, date);
    }

    @Override
    public List<Appointment> getPastAppointments(int doctorId) {
        java.time.LocalDate currentDate = java.time.LocalDate.now();
        String date = currentDate.toString();
        return findByDoctorIdAndDateLessThan(doctorId, date);
    }

    @Override
    public List<Appointment> getPatientAppointments(int patientId) {
        return findByPatientId(patientId);
    }

    @Override
    public boolean isTimeSlotAvailable(int doctorId, String date, String time) {
        try {
            Time sqlTime = Time.valueOf(time);
            return !existsByDoctorIdAndDateAndTime(doctorId, date, sqlTime);
        } catch (IllegalArgumentException e) {
            // Handle invalid time format
            return false;
        }
    }


}