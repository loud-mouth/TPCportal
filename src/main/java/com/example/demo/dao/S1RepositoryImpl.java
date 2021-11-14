package com.example.demo.dao;

import com.example.demo.models.S1;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class S1RepositoryImpl implements S1Repository {
    private static final String getS1 = "SELECT * FROM JobProfile AS J"
            + " INNER JOIN company AS P ON J.companyId=P.companyId INNER JOIN eligiblebranches as EB ON J.jobProfileId = EB.jobProfileId INNER JOIN PPT ON PPT.jobProfileId=J.jobProfileId"
            + " WHERE J.numberOfRounds=\"1\" AND J.typeOfProfile=? AND J.academicSession=? AND J.cgpaCutoff<=? AND EB.branch=?"
            + " AND J.JobProfileId NOT IN (select SL.jobProfileId from shortlist as SL where SL.studentId=? and SL.roundNumber=\"1\")";

    private static final String getS2 = "SELECT * FROM JobProfile AS J INNER JOIN company AS P ON J.companyId=P.companyId INNER JOIN eligiblebranches as EB ON J.jobProfileId = EB.jobProfileId "
            + " INNER JOIN PPT ON PPT.jobProfileId=J.jobProfileId WHERE J.numberOfRounds=\"1\" AND	J.typeOfProfile=? AND J.academicSession=? AND J.cgpaCutoff<=? AND EB.branch=? "
            + " AND J.JobProfileId IN (select SL.jobProfileId from shortlist as SL where SL.studentId=? and SL.roundNumber=\"1\");";



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<S1> getS1(Student student)
    {
        try
        {
            List<S1> S1list = jdbcTemplate.query(getS1, new Object[] {student.getSittingFor(), student.getAcademicSession(), student.getCgpa(), student.getBranch(), student.getStudentId()}, new S1rowMapper());
            return S1list;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<S1> getS2(Student student)
    {
        try
        {
            List<S1> S2list = jdbcTemplate.query(getS2, new Object[] {student.getSittingFor(), student.getAcademicSession(), student.getCgpa(), student.getBranch(), student.getStudentId()}, new S1rowMapper());
            System.out.println(S2list.size() + " repsonses");
            return S2list;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

