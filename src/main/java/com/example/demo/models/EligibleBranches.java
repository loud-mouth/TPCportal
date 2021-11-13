package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EligibleBranches {
    private int jobProfileId;
    private String branch;

    public int getJobProfileId() {
        return jobProfileId;
    }

    public void setJobProfileId(int jobProfileId) {
        this.jobProfileId = jobProfileId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
