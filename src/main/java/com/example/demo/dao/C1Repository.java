package com.example.demo.dao;

import com.example.demo.models.C1;
import com.example.demo.models.Company;
import com.example.demo.models.JobProfile;
import com.example.demo.models.Student;

import java.util.List;

public interface C1Repository {
    List<C1> getC1(JobProfile jobProfile);
}
