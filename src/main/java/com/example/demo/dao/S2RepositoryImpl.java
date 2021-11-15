package com.example.demo.dao;

import com.example.demo.models.S2;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class S2RepositoryImpl implements S2Repository {
    private static final String getActiveCodingTests = "SELECT *\n" +
            "\t\tFROM JobProfile AS J \n" +
            "        INNER JOIN company AS P \n" +
            "\t\t\tON J.companyId=P.companyId\n" +
            "\t\tINNER JOIN shortlist as SL\n" +
            "\t\t\tON ((SL.jobProfileId = J.jobProfileId) AND (SL.roundNumber = J.numberOfRounds))\n" +
            " \t\tINNER JOIN CodingTest as T\n" +
            " \t\t\tON ((T.jobProfileId = J.jobProfileId) AND (T.roundNumber = SL.roundNumber))\n" +
            "        WHERE\n" +
            "\t\t\tSL.studentId = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<S2> getActiveCodingTests(Student student)
    {
        try
        {
            List<S2> S2list = jdbcTemplate.query(getActiveCodingTests, new Object[] {student.getStudentId()}, new S2rowMapper());
            return S2list;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

