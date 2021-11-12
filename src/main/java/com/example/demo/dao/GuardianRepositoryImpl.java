package com.example.demo.dao;

import com.example.demo.models.Guardian;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuardianRepositoryImpl implements GuardianRepository {
    private static final String saveGuardian = "INSERT INTO Guardian VALUES(?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Guardian saveGuardian(Guardian guardian)
    {
        try
        {
            jdbcTemplate.update(saveGuardian, guardian.getName(), guardian.getPhoneNumber(), guardian.getStudentId());
            return guardian;
        }
        catch(DataAccessException e)
        {
            Guardian g1 = new Guardian();
            g1.setStudentId(-1);
            return g1;
        }
    }

}
