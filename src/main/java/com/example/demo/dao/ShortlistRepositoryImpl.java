package com.example.demo.dao;

import com.example.demo.models.Shortlist;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShortlistRepositoryImpl implements ShortlistRepository {
    private static final String saveShortlist = "INSERT INTO Shortlist VALUES(?, ?, ?, ?, ?)";
    private static final String updateShortlist = "UPDATE Shortlist SET score = ? WHERE studentId=? AND jobProfileId=? AND roundNumber=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Shortlist saveShortlist(Shortlist shortlist)
    {
        try
        {
            jdbcTemplate.update(saveShortlist, shortlist.getStudentId(), shortlist.getJobProfileId(), shortlist.getRoundNumber(), shortlist.getResumeLink(), shortlist.getScore());
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

    @Override
    public Shortlist updateShortlist(Shortlist shortlist)
    {
        try
        {
            System.out.println("HERE WE ARE " + shortlist.getRoundNumber());
            jdbcTemplate.update(updateShortlist, shortlist.getScore(), shortlist.getStudentId(), shortlist.getJobProfileId(), shortlist.getRoundNumber());

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
