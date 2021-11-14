package com.example.demo.dao;


import com.example.demo.models.Company;
import com.example.demo.models.Interview;
import com.example.demo.models.Interviewer;

import java.util.List;

public interface InterviewerRepository {
    Interviewer saveInterviewer(Interviewer interviewer);
    List<Interviewer> getAll(Company company);
}
