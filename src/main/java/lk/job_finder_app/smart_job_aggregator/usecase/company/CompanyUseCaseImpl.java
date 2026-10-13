package lk.job_finder_app.smart_job_aggregator.usecase.company;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.GetAllCompaniesResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public class CompanyUseCaseImpl implements CompanyUseCase {

    //inject company repository
    private final CompanyRepository companyRepository;

    //get all companies
    @Override
    public List<GetAllCompaniesResult> getAllCompanies(){
        return  companyRepository.getAllCompanies().stream()
                .map(company -> new GetAllCompaniesResult(
                        company.getCompanyId().toString(),
                        company.getCompanyName(),
                        company.getCompanyIndustry(),
                        company.getCompanyRating().toString()
                )).toList();
    }


    //create company
    @Override
    public Company createCompany(Company company){
        return companyRepository.createCompany(company);
    }

    //update company
    @Override
    public Company updateCompany(Long companyId, Company company){
        //check company availability by custom method
        companyRepository.getCompanyById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found" + " " +companyId));
        //set values to domain repo
        return companyRepository.updateCompany(companyId, company);
    }

    //delete company
    public void deleteCompany(Long companyId){
        //check availability of company
        companyRepository.getCompanyById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found" + " " + companyId));
        //then delete
        companyRepository.deleteCompany(companyId);
    }
}
