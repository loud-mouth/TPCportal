package com.example.demo.dao;

import com.example.demo.models.Interview;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InterviewRepositoryImpl implements InterviewRepository {
    private static final String saveInterview = "INSERT INTO Interview(studentId, jobProfileId, interviewerId, meetingLink, roundNumber, startDateTime, endDateTime)" +
            " VALUES(?, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveInterview(Interview interview)
    {
        try
        {
            jdbcTemplate.update(saveInterview, interview.getStudentId(), interview.getJobProfileId(), interview.getInterviewerId(), interview.getMeetingLink(),
                                    interview.getRoundNumber(), interview.getStartDateTime(), interview.getEndDateTime());
            return 1;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
