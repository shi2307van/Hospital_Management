package com.hospital.backend.repositoryImpl;

import com.hospital.backend.entity.Doctor;
import com.hospital.backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Doctor> doctorRowMapper = new RowMapper<Doctor>() {
        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Doctor doctor = new Doctor();
            doctor.setDR_ID(rs.getInt("DR_ID"));
            doctor.setDr_name(rs.getString("Dr_name"));
            doctor.setMobile_no(rs.getString("Mobile_no"));
            doctor.setEmail_id(rs.getString("Email"));
            doctor.setGender(rs.getString("Gender"));
            doctor.setAge(rs.getInt("Age"));
            doctor.setExperience(rs.getInt("Experience"));
            doctor.setPassword(rs.getString("Password"));
            doctor.setSp_Id(rs.getInt("Sp_Id"));
            return doctor;
        }
    };

    @Override
    public Optional<Doctor> findById(int id) {
        String sql = "SELECT * FROM doctor WHERE DR_ID = ?";
        List<Doctor> doctors = jdbcTemplate.query(sql, doctorRowMapper, id);
        return doctors.isEmpty() ? Optional.empty() : Optional.of(doctors.get(0));
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        String sql = "SELECT * FROM doctor WHERE Email = ?";
        List<Doctor> doctors = jdbcTemplate.query(sql, doctorRowMapper, email);
        return doctors.isEmpty() ? Optional.empty() : Optional.of(doctors.get(0));
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM doctor WHERE Email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public List<Doctor> findBySpecialization(String specialization) {
        String sql = "SELECT d.* FROM doctor d JOIN specializations s ON d.Sp_Id = s.ID WHERE s.name = ?";
        return jdbcTemplate.query(sql, doctorRowMapper, specialization);
    }

    @Override
    public List<Doctor> findByStatus(String status) {
        // Assuming there's a status field, if not we could adapt this method
        String sql = "SELECT * FROM doctor WHERE status = ?";
        return jdbcTemplate.query(sql, doctorRowMapper, status);
    }

    @Override
    public List<Doctor> findByStatusAndSpecialization(String status, String specialization) {
        String sql = "SELECT d.* FROM doctor d JOIN specializations s ON d.Sp_Id = s.ID WHERE d.status = ? AND s.name = ?";
        return jdbcTemplate.query(sql, doctorRowMapper, status, specialization);
    }

    @Override
    public List<Doctor> findByNameContainingIgnoreCase(String name) {
        String sql = "SELECT * FROM doctor WHERE LOWER(Dr_name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, doctorRowMapper, "%" + name + "%");
    }

    @Override
    public Doctor save(Doctor doctor) {
        if (doctor.getDR_ID() == 0) {
            String sql = "INSERT INTO doctor (Dr_name, Mobile_no, Email, Gender, Age, Experience, Password, Sp_Id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, 
                doctor.getDr_name(),
                doctor.getMobile_no(),
                doctor.getEmail_id(),
                doctor.getGender(),
                doctor.getAge(),
                doctor.getExperience(),
                doctor.getPassword(),
                doctor.getSp_Id());
            return doctor;
        } else {
            String sql = "UPDATE doctor SET Dr_name = ?, Mobile_no = ?, Email = ?, Gender = ?, " +
                        "Age = ?, Experience = ?, Password = ?, Sp_Id = ? WHERE DR_ID = ?";
            jdbcTemplate.update(sql,
                doctor.getDr_name(),
                doctor.getMobile_no(),
                doctor.getEmail_id(),
                doctor.getGender(),
                doctor.getAge(),
                doctor.getExperience(),
                doctor.getPassword(),
                doctor.getSp_Id(),
                doctor.getDR_ID());
            return doctor;
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM doctor WHERE DR_ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Doctor> findAll() {
        String sql = "SELECT * FROM doctor";
        return jdbcTemplate.query(sql, doctorRowMapper);
    }

    @Override
    public Doctor register(Doctor doctor) {
        return save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor) {
        if (doctor.getDR_ID() == 0) {
            throw new IllegalArgumentException("Doctor ID must be provided for update");
        }
        return save(doctor);
    }

    @Override
    public Doctor changeStatus(int id, String status) {
        // If there's no status field, this method might need to be adapted
        String sql = "UPDATE doctor SET status = ? WHERE DR_ID = ?";
        jdbcTemplate.update(sql, status, id);
        
        return findById(id).orElse(null);
    }

    @Override
    public List<Doctor> getActiveDoctors() {
        // If there's no status field, this method might need to be adapted
        return findByStatus("Active");
    }

    @Override
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return findBySpecialization(specialization);
    }
    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM doctor WHERE DR_ID = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
}