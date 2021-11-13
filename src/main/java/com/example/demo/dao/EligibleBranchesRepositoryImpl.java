package com.example.demo.dao;

import com.example.demo.models.EligibleBranches;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EligibleBranchesRepositoryImpl implements EligibleBranchesRepository {
    private static final String saveEligibleBranches = "INSERT INTO EligibleBranches VALUES(?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int saveEligibility(String branch, JobProfile jobProfile)
    {
        try
        {
            jdbcTemplate.update(saveEligibleBranches, jobProfile.getJobProfileId(), branch);
            return 1;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            return -1;
        }
    }

}
