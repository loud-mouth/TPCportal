package com.example.demo.dao;

import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private static final String getStudentByStudentId = "SELECT * FROM Student WHERE studentId=?";
    private static final String saveStudent = "INSERT INTO Student(studentId, name, emailId, password, address, dob, phoneNumber, academicSession, branch, course, cgpa, TPRid, sittingFor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                student.setDob(rs.getDate(6));
                student.setPhoneNumber(rs.getString(7));
                student.setAcademicSession(rs.getInt(8));
                student.setBranch(rs.getString(9));
                student.setCourse(rs.getString(10));
                student.setCgpa(rs.getFloat(11));
                student.setTPRid(rs.getInt(12));
                student.setSittingFor(rs.getString(13));
                return student;
            }, studentId);
        }
        catch(DataAccessException e) {
            System.out.println(e.getMessage());
            Student student = new Student();
            student.setStudentId(-1);
            return student;
        }
    }

    @Override
    public Student saveStudent(Student student)
    {
        try
        {
            jdbcTemplate.update(saveStudent, student.getStudentId(), student.getName(), student.getEmail(), student.getPassword(), student.getAddress(), student.getDob(), student.getPhoneNumber(),
                    student.getAcademicSession(), student.getBranch(), student.getCourse(), student.getCgpa(), student.getTPRid(), student.getSittingFor());

            return student;
        }
        catch(DataAccessException e)
        {
            Student student1 = new Student();
            student1.setStudentId(-1);
            return student1;
        }
    }




}
