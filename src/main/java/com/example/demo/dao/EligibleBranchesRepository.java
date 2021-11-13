package com.example.demo.dao;

import com.example.demo.models.JobProfile;

public interface EligibleBranchesRepository {
    int saveEligibility(String branch, JobProfile jobProfile);
}
