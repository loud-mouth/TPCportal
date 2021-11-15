package com.example.demo.dao;

import com.example.demo.models.S2;
import com.example.demo.models.Student;

import java.util.List;

public interface S2Repository {
    List<S2> getActiveCodingTests(Student student);
}
