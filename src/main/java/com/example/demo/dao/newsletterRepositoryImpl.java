package com.example.demo.dao;

import com.example.demo.models.newsletter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class newsletterRepositoryImpl implements newsletterRepository {
    private static final String addEmailId = "INSERT INTO newsletter VALUES (?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addEmailId(String emailId)
    {
        try
        {
            jdbcTemplate.update(addEmailId, emailId);
            return 1;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}