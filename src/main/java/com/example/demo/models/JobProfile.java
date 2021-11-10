//done
package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobProfile {
    private int jobProfileId;
    private int companyId;

    private float cgpaCutoff;
    private int numberOfRounds;

    private String typeOfProfile;
    private int academicSession;

    private int stipend;
    private String position;
    private String description;
    private String location;
    private String duration;

    public String getTypeOfProfile() {
        return typeOfProfile;
    }

    public void setTypeOfProfile(String typeOfProfile) {
        this.typeOfProfile = typeOfProfile;
    }

    public int getJobProfileId() {
        return jobProfileId;
    }

    public void setJobProfileId(int jobProfileId) {
        this.jobProfileId = jobProfileId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getStipend() {
        return stipend;
    }

    public void setStipend(int stipend) {
        this.stipend = stipend;
    }

    public int getAcademicSession() {
        return academicSession;
    }

    public void setAcademicSession(int academicSession) {
        this.academicSession = academicSession;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public float getCgpaCutoff() {
        return cgpaCutoff;
    }

    public void setCgpaCutoff(float cgpaCutoff) {
        this.cgpaCutoff = cgpaCutoff;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }
}
