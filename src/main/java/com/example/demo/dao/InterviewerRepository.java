package com.example.demo.dao;


import com.example.demo.models.Interview;
import com.example.demo.models.Interviewer;

public interface InterviewerRepository {
    Interviewer saveInterviewer(Interviewer interviewer);
}
