package com.example.demo.dao;

import com.example.demo.models.Interviewer;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InterviewerRepositoryImpl implements InterviewerRepository {
    private static final String saveInterviewer = "INSERT INTO Interviewer(name, phoneNumber, companyId) VALUES(?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Interviewer saveInterviewer(Interviewer interviewer)
    {
        try
        {
            jdbcTemplate.update(saveInterviewer, interviewer.getName(), interviewer.getPhoneNumber(), interviewer.getCompanyId());
            return interviewer;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            Interviewer g1 = new Interviewer();
            g1.setInterviewerId(-1);
            return g1;
        }
    }

}
