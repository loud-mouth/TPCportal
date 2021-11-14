package com.example.demo.dao;

import com.example.demo.models.*;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class S1rowMapper implements RowMapper<S1> {

    @Override
    public S1 mapRow(ResultSet rs, int rowNum) throws SQLException {
        JobProfile jobProfile = (new BeanPropertyRowMapper<>(JobProfile.class)).mapRow(rs, rowNum);
        Company company = (new BeanPropertyRowMapper<>(Company.class)).mapRow(rs, rowNum);
        EligibleBranches eligibleBranches = (new BeanPropertyRowMapper<>(EligibleBranches.class)).mapRow(rs, rowNum);
        PPT ppt = (new BeanPropertyRowMapper<>(PPT.class)).mapRow(rs, rowNum);

        S1 s1 = new S1();
        s1.setCompany(company);
        s1.setJobProfile(jobProfile);
        s1.setPpt(ppt);
        s1.setEligibleBranches(eligibleBranches);
        return s1;
    }
}
