package com.example.demo.dao;

import com.example.demo.models.PPT;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PptRepositoryImpl implements PptRepository {
    private static final String savePPT = "INSERT INTO PPT(jobProfileId, dateTime, meetingLink) VALUES(?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PPT savePPT(PPT ppt)
    {
        try
        {
            jdbcTemplate.update(savePPT, ppt.getJobProfileId(), ppt.getDateTime(), ppt.getMeetingLink());
            return ppt;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            PPT g1 = new PPT();
            g1.setJobProfileId(-1);
            return g1;
        }
    }

}
