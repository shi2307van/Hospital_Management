package com.hospital.backend.repositoryImpl;

import com.hospital.backend.entity.Prescription;
import com.hospital.backend.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PrescriptionRepositoryImpl implements PrescriptionRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Prescription> prescriptionRowMapper = new RowMapper<Prescription>() {
        @Override
        public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
            Prescription prescription = new Prescription();
            prescription.setPr_ID(rs.getInt("Pr_ID"));
            prescription.setAp_Id(rs.getInt("Ap_Id"));
            prescription.setP_ID(rs.getInt("P_ID"));
            prescription.setMedicine(rs.getString("medicine"));
            prescription.setAdvice(rs.getString("advice"));
            prescription.setRemark(rs.getString("remark"));
            return prescription;
        }
    };

    @Override
    public List<Prescription> findByDoctorId(int doctorId) {
        // Join with appointments to get doctor ID
        String sql = "SELECT p.* FROM prescriptions p JOIN appointments a ON p.Ap_Id = a.Ap_ID WHERE a.D_ID = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, doctorId);
    }

    @Override
    public List<Prescription> findByPatientId(int patientId) {
        String sql = "SELECT * FROM prescriptions WHERE P_ID = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, patientId);
    }

    @Override
    public List<Prescription> findByAppointmentId(int appointmentId) {
        String sql = "SELECT * FROM prescriptions WHERE Ap_Id = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, appointmentId);
    }

    @Override
    public List<Prescription> findByPatientIdAndStatus(int patientId, String status) {
        // Assuming prescriptions don't have a status or we check appointment's status
        String sql = "SELECT p.* FROM prescriptions p JOIN appointments a ON p.Ap_Id = a.Ap_ID WHERE p.P_ID = ? AND a.status = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, patientId, status);
    }

    @Override
    public List<Prescription> findByDate(String date) {
        // Assuming we need to join with appointments to get the date
        String sql = "SELECT p.* FROM prescriptions p JOIN appointments a ON p.Ap_Id = a.Ap_ID WHERE a.appointment_date = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, date);
    }

    @Override
    public List<Prescription> findByDoctorIdAndDate(int doctorId, String date) {
        // Join with appointments to get doctor ID and date
        String sql = "SELECT p.* FROM prescriptions p JOIN appointments a ON p.Ap_Id = a.Ap_ID WHERE a.D_ID = ? AND a.appointment_date = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, doctorId, date);
    }

    @Override
    public List<Prescription> findByPatientIdAndDate(int patientId, String date) {
        // Join with appointments to get date
        String sql = "SELECT p.* FROM prescriptions p JOIN appointments a ON p.Ap_Id = a.Ap_ID WHERE p.P_ID = ? AND a.appointment_date = ?";
        return jdbcTemplate.query(sql, prescriptionRowMapper, patientId, date);
    }

    @Override
    public Prescription save(Prescription prescription) {
        if (prescription.getPr_ID() == 0) {
            String sql = "INSERT INTO prescriptions (Ap_Id, P_ID, medicine, advice, remark) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, 
                prescription.getAp_Id(),
                prescription.getP_ID(),
                prescription.getMedicine(),
                prescription.getAdvice(),
                prescription.getRemark());
            return prescription;
        } else {
            String sql = "UPDATE prescriptions SET Ap_Id = ?, P_ID = ?, medicine = ?, advice = ?, remark = ? WHERE Pr_ID = ?";
            jdbcTemplate.update(sql,
                prescription.getAp_Id(),
                prescription.getP_ID(),
                prescription.getMedicine(),
                prescription.getAdvice(),
                prescription.getRemark(),
                prescription.getPr_ID());
            return prescription;
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM prescriptions WHERE Pr_ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Prescription> findAll() {
        String sql = "SELECT * FROM prescriptions";
        return jdbcTemplate.query(sql, prescriptionRowMapper);
    }

    @Override
    public Prescription createPrescription(Prescription prescription) {
        return save(prescription);
    }

    @Override
    public Prescription updatePrescription(Prescription prescription) {
        if (prescription.getPr_ID() == 0) {
            throw new IllegalArgumentException("Prescription ID must be provided for update");
        }
        return save(prescription);
    }

    @Override
    public Prescription changePrescriptionStatus(int id, String status) {
        // Prescriptions don't have a status field, so this might be implemented differently
        // Maybe update an appointment status instead?
        return null;
    }

    @Override
    public List<Prescription> getPatientPrescriptions(int patientId) {
        return findByPatientId(patientId);
    }

    @Override
    public List<Prescription> getDoctorPrescriptions(int doctorId) {
        return findByDoctorId(doctorId);
    }

    @Override
    public List<Prescription> getPrescriptionsByAppointment(int appointmentId) {
        return findByAppointmentId(appointmentId);
    }

    @Override
    public Prescription getPrescriptionById(int id) {
        String sql = "SELECT * FROM prescriptions WHERE Pr_ID = ?";
        List<Prescription> prescriptions = jdbcTemplate.query(sql, prescriptionRowMapper, id);
        return prescriptions.isEmpty() ? null : prescriptions.get(0);
    }
    @Override
    public Optional<Prescription> findById(int id) {
        try {
            String sql = "SELECT * FROM prescriptions WHERE PR_ID = ?";
            Prescription prescription = jdbcTemplate.queryForObject(sql, prescriptionRowMapper, id);
            return Optional.ofNullable(prescription);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM prescriptions WHERE PR_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}