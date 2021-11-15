package com.example.demo.dao;

import com.example.demo.models.*;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class S3rowMapper implements RowMapper<S3> {

    @Override
    public S3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        JobProfile jobProfile = (new BeanPropertyRowMapper<>(JobProfile.class)).mapRow(rs, rowNum);
        Company company = (new BeanPropertyRowMapper<>(Company.class)).mapRow(rs, rowNum);
        Interview interview = (new BeanPropertyRowMapper<>(Interview.class)).mapRow(rs, rowNum);
        Shortlist shortlist = (new BeanPropertyRowMapper<>(Shortlist.class)).mapRow(rs, rowNum);

        S3 s1 = new S3();

        s1.setShortlist(shortlist);
        s1.setCompany(company);
        s1.setJobProfile(jobProfile);
        s1.setInterview(interview);
        return s1;
    }
}
