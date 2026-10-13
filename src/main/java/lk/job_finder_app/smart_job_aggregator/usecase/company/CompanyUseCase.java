package lk.job_finder_app.smart_job_aggregator.usecase.company;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.GetAllCompaniesResult;

import java.util.List;

public interface CompanyUseCase {

    //get all companies
    List<GetAllCompaniesResult> getAllCompanies();

    //create company
    Company createCompany(Company company);

    //update company
    Company updateCompany(Long companyId, Company company);

    //delete company
    void deleteCompany(Long companyId);
}
