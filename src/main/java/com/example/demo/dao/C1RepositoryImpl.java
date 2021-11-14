package com.example.demo.dao;

import com.example.demo.models.C1;
import com.example.demo.models.Company;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class C1RepositoryImpl implements C1Repository {
    private static final String getC1 = "SELECT * FROM Student AS S "
                                            + "INNER JOIN Shortlist as SL ON S.studentId = SL.studentId "
                                            + "WHERE SL.roundNumber = ? AND " +
                                                    "SL.jobProfileId = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<C1> getC1(JobProfile jobProfile)
    {
        try
        {
            List<C1> C1list = jdbcTemplate.query(getC1, new Object[] {jobProfile.getNumberOfRounds(), jobProfile.getJobProfileId()}, new C1rowMapper());
            return C1list;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }


}

