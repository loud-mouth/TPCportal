package com.example.demo.models;

public class S2 {
    private JobProfile jobProfile;
    private Company company;
    private Shortlist shortlist;
    private  CodingTest codingTest;

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

    public CodingTest getCodingTest() {
        return codingTest;
    }

    public void setCodingTest(CodingTest codingTest) {
        this.codingTest = codingTest;
    }
}
