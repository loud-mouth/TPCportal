package com.example.demo.dao;

import com.example.demo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private static final String getStudentByStudentId = "SELECT * FROM Student WHERE studentId=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Student getStudentByStudentId(int studentId)
    {
        try{
            return jdbcTemplate.queryForObject(getStudentByStudentId, (rs, rowNum)->{
                Student student = new Student();
                student.setStudentId(rs.getInt(1));
                student.setName(rs.getString(2));
                student.setEmail(rs.getString(3));
                student.setPassword(rs.getString(4));
                student.setAddress(rs.getString(5));
                student.setDob(rs.getString(6));
                student.setPhoneNumber(rs.getString(7));
                student.setGuardianId(rs.getInt(8));
                student.setAcademicSession(rs.getInt(9));
                student.setBranch(rs.getString(10));
                student.setCourse(rs.getString(11));
                student.setCgpa(rs.getFloat(12));
                student.setTPRid(rs.getInt(13));
                student.setSittingFor(rs.getString(14));
                return student;
            }, studentId);
        }
        catch(Exception e) {
            Student student = new Student();
            student.setStudentId(-1);
            return student;
        }
    }




}
