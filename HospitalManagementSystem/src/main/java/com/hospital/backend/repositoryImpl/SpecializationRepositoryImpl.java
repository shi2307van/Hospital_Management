package com.hospital.backend.repositoryImpl;

import com.hospital.backend.entity.Specialization;
import com.hospital.backend.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class SpecializationRepositoryImpl implements SpecializationRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Specialization> specializationRowMapper = new RowMapper<Specialization>() {
        @Override
        public Specialization mapRow(ResultSet rs, int rowNum) throws SQLException {
            Specialization specialization = new Specialization();
            specialization.setSp_Id(rs.getInt("Sp_Id"));
            specialization.setSp_Name(rs.getString("Sp_Name"));
            return specialization;
        }
    };



    @Override
    public Optional<Specialization> findByName(String name) {
        String sql = "SELECT * FROM specialization WHERE Sp_Name = ?";
        List<Specialization> specializations = jdbcTemplate.query(sql, specializationRowMapper, name);
        return specializations.isEmpty() ? Optional.empty() : Optional.of(specializations.get(0));
    }



    @Override
    public List<Specialization> findByNameContainingIgnoreCase(String name) {
        String sql = "SELECT * FROM specialization WHERE LOWER(Sp_Name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, specializationRowMapper, "%" + name + "%");
    }

    @Override
    public boolean existsByName(String name) {
        String sql = "SELECT COUNT(*) FROM specialization WHERE Sp_Name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;
    }

    @Override
    public Specialization save(Specialization specialization) {
        if (specialization.getSp_Id() == 0) {
            String sql = "INSERT INTO specialization (Sp_Name) VALUES (?)";
            jdbcTemplate.update(sql, specialization.getSp_Name());
            return specialization;
        } else {
            String sql = "UPDATE specialization SET Sp_Name = ? WHERE Sp_Id = ?";
            jdbcTemplate.update(sql,
                specialization.getSp_Name(),
                specialization.getSp_Id());
            return specialization;
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM specialization WHERE Sp_Id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Specialization> findAll() {
        String sql = "SELECT * FROM specializations";
        return jdbcTemplate.query(sql, specializationRowMapper);
    }

    @Override
    public Specialization createSpecialization(Specialization specialization) {
        return save(specialization);
    }

    @Override
    public Specialization updateSpecialization(Specialization specialization) {
        if (specialization.getSp_Id() == 0) {
            throw new IllegalArgumentException("Specialization ID must be provided for update");
        }
        return save(specialization);
    }



    @Override
    public List<Specialization> getSpecializationsByName(String name) {
        return findByNameContainingIgnoreCase(name);
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT COUNT(*) FROM specialization WHERE Sp_Id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }
    
    @Override
    public Optional<Specialization> findById(int id) {
        String sql = "SELECT * FROM specialization WHERE Sp_Id = ?";
        List<Specialization> specializations = jdbcTemplate.query(sql, specializationRowMapper, id);
        return specializations.isEmpty() ? Optional.empty() : Optional.of(specializations.get(0));
    }
}