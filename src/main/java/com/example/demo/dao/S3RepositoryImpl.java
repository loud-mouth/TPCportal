package com.example.demo.dao;

import com.example.demo.models.S3;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class S3RepositoryImpl implements S3Repository {
    private static final String getActiveInterviewTests = "SELECT * \n" +
            "\t\tFROM JobProfile AS J \n" +
            "        INNER JOIN company AS P \n" +
            "\t\t\tON J.companyId=P.companyId\n" +
            "\t\tINNER JOIN shortlist as SL\n" +
            "\t\t\tON ((SL.jobProfileId = J.jobProfileId) AND (SL.roundNumber = J.numberOfRounds))\n" +
            "\t\tINNER JOIN Interview as I\n" +
            "\t\t\tON ((I.jobProfileId = J.jobProfileId) AND (I.roundNumber = J.numberOfRounds))\n" +
            "\t\tWHERE\n" +
            "\t\t\tSL.studentid = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<S3> getActiveInterviewTests(Student student)
    {
        try
        {
            List<S3> S3list = jdbcTemplate.query(getActiveInterviewTests, new Object[] {student.getStudentId()}, new S3rowMapper());
            return S3list;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

