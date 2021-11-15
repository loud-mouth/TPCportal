package com.example.demo.dao;

import com.example.demo.models.*;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class S2rowMapper implements RowMapper<S2> {

    @Override
    public S2 mapRow(ResultSet rs, int rowNum) throws SQLException {
        JobProfile jobProfile = (new BeanPropertyRowMapper<>(JobProfile.class)).mapRow(rs, rowNum);
        Company company = (new BeanPropertyRowMapper<>(Company.class)).mapRow(rs, rowNum);
        Shortlist shortlist = (new BeanPropertyRowMapper<>(Shortlist.class)).mapRow(rs, rowNum);
        CodingTest codingTest = (new BeanPropertyRowMapper<>(CodingTest.class)).mapRow(rs, rowNum);

        S2 s1 = new S2();
        s1.setCompany(company);
        s1.setJobProfile(jobProfile);
        s1.setShortlist(shortlist);
        s1.setCodingTest(codingTest);
        return s1;
    }
}
