package lk.job_finder_app.smart_job_aggregator.domain.repositories;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    //check company by ID
    Optional<Company> getCompanyById(long id);

    //get all companies
    List<Company> getAllCompanies();

    //create company
    Company createCompany(Company company);

    //update company
    Company updateCompany(Company company);

    //delete company
    void deleteCompany(Long companyId);
}
