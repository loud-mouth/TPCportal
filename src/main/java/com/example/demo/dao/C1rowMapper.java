package com.example.demo.dao;

import com.example.demo.models.*;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class C1rowMapper implements RowMapper<C1> {
    @Override
    public C1 mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = (new BeanPropertyRowMapper<>(Student.class)).mapRow(rs, rowNum);
        Shortlist shortlist = (new BeanPropertyRowMapper<>(Shortlist.class)).mapRow(rs, rowNum);
        C1 c1 = new C1();
        c1.setStudent(student);
        c1.setShortlist(shortlist);
        return c1;
    }
}
