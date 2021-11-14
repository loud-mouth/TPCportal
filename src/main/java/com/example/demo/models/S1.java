package com.example.demo.models;

public class S1 {
    private JobProfile jobProfile;
    private Company company;
    private EligibleBranches eligibleBranches;
    private PPT ppt;

    public PPT getPpt() {
        return ppt;
    }

    public void setPpt(PPT ppt) {
        this.ppt = ppt;
    }

    public JobProfile getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(JobProfile jobProfile) {
        this.jobProfile = jobProfile;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public EligibleBranches getEligibleBranches() {
        return eligibleBranches;
    }

    public void setEligibleBranches(EligibleBranches eligibleBranches) {
        this.eligibleBranches = eligibleBranches;
    }
}
