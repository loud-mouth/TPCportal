package com.example.demo.dao;

import com.example.demo.models.Shortlist;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShortlistRepositoryImpl implements ShortlistRepository {
    private static final String saveShortlist = "INSERT INTO Shortlist VALUES(?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Shortlist saveShortlist(Shortlist shortlist)
    {
        try
        {
            jdbcTemplate.update(saveShortlist, shortlist.getStudentId(), shortlist.getJobProfileId(), shortlist.getRoundNumber(), shortlist.getResumeLink());
            return shortlist;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            Shortlist g1 = new Shortlist();
            g1.setStudentId(-1);
            return g1;
        }
    }

}
