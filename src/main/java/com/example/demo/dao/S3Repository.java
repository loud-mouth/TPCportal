package com.example.demo.dao;

import com.example.demo.models.S3;
import com.example.demo.models.Student;

import java.util.List;

public interface S3Repository {
    List<S3> getActiveInterviewTests(Student student);
}
