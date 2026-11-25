package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Company;

public interface CompanyService {

    Company saveCompany(Company company);

    List<Company> getAllCompanies();

    Optional<Company> getCompanyById(Long id);
}
