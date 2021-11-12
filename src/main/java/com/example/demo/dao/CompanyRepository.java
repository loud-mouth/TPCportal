package com.example.demo.dao;


import com.example.demo.models.Company;

public interface CompanyRepository {
    Company getCompanyByEmailId(String emailId);
}
