package com.example.demo.dao;


import com.example.demo.models.Guardian;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobProfileRepositoryImpl implements  JobProfileRepository {
    private static final String saveJobProfile = "INSERT INTO JobProfile(companyId, cgpaCutoff, numberOfRounds, typeOfProfile, academicSession, stipend, position, description, location, duration) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public JobProfile saveJobProfile(JobProfile jobProfile)
    {
        try
        {
            jdbcTemplate.update(saveJobProfile, jobProfile.getCompanyId(), jobProfile.getCgpaCutoff(), jobProfile.getNumberOfRounds(),
                    jobProfile.getTypeOfProfile(), jobProfile.getAcademicSession(), jobProfile.getStipend(), jobProfile.getPosition(),
                    jobProfile.getDescription(), jobProfile.getLocation(), jobProfile.getDuration());

            return jobProfile;
        }
        catch(DataAccessException e)
        {
            System.out.println(e.getMessage());
            JobProfile g1 = new JobProfile();
            g1.setJobProfileId(-1);
            return g1;
        }
    }

}

