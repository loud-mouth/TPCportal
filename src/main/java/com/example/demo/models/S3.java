package com.example.demo.models;

public class S3 {
    private JobProfile jobProfile;
    private Company company;
    private Shortlist shortlist;
    private  Interview interview;

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

    public Shortlist getShortlist() {
        return shortlist;
    }

    public void setShortlist(Shortlist shortlist) {
        this.shortlist = shortlist;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }
}
