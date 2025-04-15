package com.hospital.backend.repositoryImpl;

import com.hospital.backend.entity.Patient;
import com.hospital.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Patient> patientRowMapper = new RowMapper<Patient>() {
        @Override
        public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Patient patient = new Patient();
            patient.setpId(rs.getInt("pId"));
            patient.setName(rs.getString("name"));
            patient.setDob(rs.getObject("dob", LocalDate.class));
            patient.setAge(rs.getInt("age"));
            patient.setGender(rs.getString("gender"));
            patient.setBloodGroup(rs.getString("bloodGroup"));
            patient.setMobileNo(rs.getString("mobileNo"));
            patient.setEmail(rs.getString("email"));
            patient.setAddress(rs.getString("address"));
            patient.setPassword(rs.getString("password"));
            return patient;
        }
    };

    @Override
    public Optional<Patient> findById(int id) {
        String sql = "SELECT * FROM patient WHERE pId = ?";
        List<Patient> patients = jdbcTemplate.query(sql, patientRowMapper, id);
        return patients.isEmpty() ? Optional.empty() : Optional.of(patients.get(0));
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        String sql = "SELECT * FROM patient WHERE email = ?";
        List<Patient> patients = jdbcTemplate.query(sql, patientRowMapper, email);
        return patients.isEmpty() ? Optional.empty() : Optional.of(patients.get(0));
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM patient WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public List<Patient> findByBloodGroup(String bloodGroup) {
        String sql = "SELECT * FROM patient WHERE bloodGroup = ?";
        return jdbcTemplate.query(sql, patientRowMapper, bloodGroup);
    }

    @Override
    public List<Patient> findByStatus(String status) {
        // Assuming there's a status field or using an alternative implementation
        String sql = "SELECT * FROM patient WHERE status = ?";
        return jdbcTemplate.query(sql, patientRowMapper, status);
    }

    @Override
    public List<Patient> findByNameContainingIgnoreCase(String name) {
        String sql = "SELECT * FROM patient WHERE LOWER(name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, patientRowMapper, "%" + name + "%");
    }

    @Override
    public List<Patient> findByContactContaining(String contact) {
        String sql = "SELECT * FROM patient WHERE mobileNo LIKE ?";
        return jdbcTemplate.query(sql, patientRowMapper, "%" + contact + "%");
    }

    @Override
    public Patient save(Patient patient) {
        if (patient.getpId() == 0) {
            String sql = "INSERT INTO patient (name, dob, age, gender, bloodGroup, mobileNo, email, address, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, 
                patient.getName(),
                patient.getDob(),
                patient.getAge(),
                patient.getGender(),
                patient.getBloodGroup(),
                patient.getMobileNo(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getPassword());
            return patient;
        } else {
            String sql = "UPDATE patient SET name = ?, dob = ?, age = ?, gender = ?, " +
                        "bloodGroup = ?, mobileNo = ?, email = ?, address = ?, password = ? WHERE pId = ?";
            jdbcTemplate.update(sql,
                patient.getName(),
                patient.getDob(),
                patient.getAge(),
                patient.getGender(),
                patient.getBloodGroup(),
                patient.getMobileNo(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getPassword(),
                patient.getpId());
            return patient;
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM patient WHERE pId = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Patient> findAll() {
        String sql = "SELECT * FROM patient";
        return jdbcTemplate.query(sql, patientRowMapper);
    }

    @Override
    public Patient register(Patient patient) {
        // Set any default values if needed
        return save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        if (patient.getpId() == 0) {
            throw new IllegalArgumentException("Patient ID must be provided for update");
        }
        return save(patient);
    }



    @Override
    public List<Patient> getActivePatients() {
        // Assuming there's a status field or this method is not needed
        return findByStatus("Active");
    }

    @Override
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return findByBloodGroup(bloodGroup);
    }

    @Override
    public Patient updateMedicalHistory(int id, String medicalHistory) {
        // Assuming there's a medicalHistory field or this method is not needed
        String sql = "UPDATE patient SET medicalHistory = ? WHERE pId = ?";
        jdbcTemplate.update(sql, medicalHistory, id);
        
        return findById(id).orElse(null);
    }
    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM patient WHERE pId = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}