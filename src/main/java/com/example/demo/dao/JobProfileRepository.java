package com.example.demo.dao;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;

import java.util.List;


public interface JobProfileRepository {
    int saveJobProfile(JobProfile jobProfile);

    List<JobProfile> getJobProfilesByCompanyId(int companyId);

    List<JobProfile> getJobProfilesAvailableToStudent(Student student);

    JobProfile getJobProfilesByJobProfileId(int jobProfileId);

    void increaseRound(JobProfile jobProfile);
}
