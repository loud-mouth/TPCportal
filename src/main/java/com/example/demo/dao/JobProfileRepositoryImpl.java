package com.example.demo.dao;


import com.example.demo.models.Guardian;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class JobProfileRepositoryImpl implements  JobProfileRepository
{
    private static final String saveJobProfile = "INSERT INTO JobProfile(companyId, cgpaCutoff, numberOfRounds, typeOfProfile, academicSession, stipend, position, description, location, duration) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static  final String getJobProfilesByCompanyId = "SELECT * FROM JobProfile WHERE companyId=?";
    private static final String getJobProfilesAvailableToStudent = "SELECT * FROM JobProfile WHERE typeOfProfile=? AND academicSession=? AND cgpaCutoff<=?";
    private static  final String getJobProfilesByJobProfileId = "SELECT * FROM JobProfile WHERE jobProfileId=?";


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

    @Override
    public List<JobProfile> getJobProfilesByCompanyId(int companyId) {
        System.out.println("Searching profiles for Company number " + companyId);
        return jdbcTemplate.query(getJobProfilesByCompanyId,(rs,rowNum)->{
            JobProfile jobProfile = new JobProfile();
            jobProfile.setJobProfileId(rs.getInt(1));
            jobProfile.setCompanyId(rs.getInt(2));
            jobProfile.setCgpaCutoff(rs.getFloat(3));
            jobProfile.setNumberOfRounds(rs.getInt(4));
            jobProfile.setTypeOfProfile(rs.getString(5));
            jobProfile.setAcademicSession(rs.getInt(6));
            jobProfile.setStipend(rs.getInt(7));
            jobProfile.setPosition(rs.getString(8));
            jobProfile.setDescription(rs.getString(9));
            jobProfile.setLocation(rs.getString(10));
            jobProfile.setDuration(rs.getString(11));
            System.out.println("Found one!");
            return jobProfile;
        }, companyId);
    }

    @Override
    public JobProfile getJobProfilesByJobProfileId(int jobProfileId) {
        System.out.println("Searching profiles for jobProfile number " + jobProfileId);
        return jdbcTemplate.queryForObject(getJobProfilesByJobProfileId,(rs,rowNum)->{
            JobProfile jobProfile = new JobProfile();
            jobProfile.setJobProfileId(rs.getInt(1));
            jobProfile.setCompanyId(rs.getInt(2));
            jobProfile.setCgpaCutoff(rs.getFloat(3));
            jobProfile.setNumberOfRounds(rs.getInt(4));
            jobProfile.setTypeOfProfile(rs.getString(5));
            jobProfile.setAcademicSession(rs.getInt(6));
            jobProfile.setStipend(rs.getInt(7));
            jobProfile.setPosition(rs.getString(8));
            jobProfile.setDescription(rs.getString(9));
            jobProfile.setLocation(rs.getString(10));
            jobProfile.setDuration(rs.getString(11));
            System.out.println("Found one!");
            return jobProfile;
        }, jobProfileId);
    }

    @Override
    public List<JobProfile> getJobProfilesAvailableToStudent(Student student) {
        return jdbcTemplate.query(getJobProfilesAvailableToStudent, (rs, rowNum)->{
            JobProfile jobProfile = new JobProfile();
            jobProfile.setJobProfileId(rs.getInt(1));
            jobProfile.setCompanyId(rs.getInt(2));
            jobProfile.setCgpaCutoff(rs.getFloat(3));
            jobProfile.setNumberOfRounds(rs.getInt(4));
            jobProfile.setTypeOfProfile(rs.getString(5));
            jobProfile.setAcademicSession(rs.getInt(6));
            jobProfile.setStipend(rs.getInt(7));
            jobProfile.setPosition(rs.getString(8));
            jobProfile.setDescription(rs.getString(9));
            jobProfile.setLocation(rs.getString(10));
            jobProfile.setDuration(rs.getString(11));
            return jobProfile;
        },  student.getSittingFor(), student.getAcademicSession(), student.getCgpa());
    }


}

