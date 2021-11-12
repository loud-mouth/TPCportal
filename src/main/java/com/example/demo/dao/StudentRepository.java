package com.example.demo.dao;

import com.example.demo.models.Student;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository {
    Student getStudentByStudentId(int studentId);
    Student saveStudent(Student student);
}
