package com.example.demo.dao;

import com.example.demo.models.S1;
import com.example.demo.models.Student;

import java.util.List;

public interface S1Repository {
    List<S1> getS1(Student student);
}
