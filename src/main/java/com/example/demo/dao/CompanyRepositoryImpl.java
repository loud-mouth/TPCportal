package com.example.demo.dao;

import com.example.demo.models.Company;
import com.example.demo.models.Guardian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepositoryImpl implements CompanyRepository {
    private static final String getCompanyByEmailId = "SELECT * FROM Company WHERE emailId=?";
    private static final String saveCompany = "INSERT INTO Company(name, repContact, emailId, password, headOffice, website) VALUES (?, ?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Company getCompanyByEmailId(String emailId)
    {
        try{
            return jdbcTemplate.queryForObject(getCompanyByEmailId, (rs, rowNum)->{
                Company company = new Company();
                company.setCompanyId(rs.getInt(1));
                company.setName(rs.getString(2));
                company.setRepContact(rs.getString(3));
                company.setEmailId(rs.getString(4));
                company.setPassword(rs.getString(5));
                company.setHeadOffice(rs.getString(6));
                company.setWebsite(rs.getString(7));
                return company;

            }, emailId);
        }
        catch(Exception e) {
            Company company = new Company();
            company.setCompanyId(-1);
            return company;
        }
    }


    @Override
    public Company saveCompany(Company company)
    {
        try
        {
            jdbcTemplate.update(saveCompany, company.getName(), company.getRepContact(), company.getEmailId(), company.getPassword(), company.getHeadOffice(), company.getWebsite());
            return company;
        }
        catch(DataAccessException e)
        {
            Company g1 = new Company();
            g1.setCompanyId(-1);
            return g1;
        }
    }




}
