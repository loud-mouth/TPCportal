package com.example.demo.dao;

import com.example.demo.models.CodingTest;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CodingTestRepositoryImpl implements CodingTestRepository {
    private static final String saveCodingTest = "INSERT INTO CodingTest(roundNumber, jobProfileId, testLink, platform, testDateTime) VALUES(?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveCodingTest(CodingTest codingTest)
    {
        try
        {
            jdbcTemplate.update(saveCodingTest, codingTest.getRoundNumber(), codingTest.getJobProfileId(), codingTest.getTestLink(),
                                            codingTest.getPlatform(), codingTest.getTestDateTime());
            return 1;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
