package lk.job_finder_app.smart_job_aggregator.usecase.company;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.*;

import java.util.List;

public interface CompanyUseCase {

    //get all companies
    List<GetAllCompaniesResult> getAllCompanies();

    //create company
    CreateCompanyResult createCompany(CreateCompanyCommand createCompanyCommand);

    //update company
    UpdateCompanyResult updateCompany(UpdateCompanyCommand updateCompanyCommand);

    //delete company
    void deleteCompany(Long companyId);
}
