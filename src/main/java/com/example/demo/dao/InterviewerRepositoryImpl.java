package com.example.demo.dao;

import com.example.demo.models.Company;
import com.example.demo.models.Interviewer;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InterviewerRepositoryImpl implements InterviewerRepository {
    private static final String saveInterviewer = "INSERT INTO Interviewer(name, phoneNumber, companyId) VALUES(?, ?, ?)";
    private static final String getInterviewersByCompanyId = "SELECT * FROM Interviewer WHERE companyId = ?";

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

    @Override
    public List<Interviewer> getAll(Company company) {
        System.out.println("Searching interviewers with Company Id " + company.getCompanyId());

        return jdbcTemplate.query(getInterviewersByCompanyId,(rs,rowNum)->{
            Interviewer inter = new Interviewer();
            inter.setInterviewerId(rs.getInt(1));
            inter.setCompanyId(rs.getInt(4));
            inter.setName(rs.getString(2));
            inter.setPhoneNumber(rs.getString(3));
            return inter;
        }, company.getCompanyId());
    }

}
