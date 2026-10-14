package lk.job_finder_app.smart_job_aggregator.usecase.company;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.*;
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
    public CreateCompanyResult createCompany(CreateCompanyCommand createCompanyCommand){
        //validate incoming fields
        if(createCompanyCommand.companyIndustry() == null
                || createCompanyCommand.companyIndustry().isEmpty()
                || createCompanyCommand.companyRating() == null
                || createCompanyCommand.companyName() == null
                || createCompanyCommand.companyName().isEmpty()
        ){
            throw new IllegalArgumentException("Required parameters cannot be empty");
        }
        //create model
        Company createNewCompany = Company.createCompany(
                createCompanyCommand.companyName(),
                createCompanyCommand.companyIndustry(),
                createCompanyCommand.companyRating()
        );

        Company savedCompany = companyRepository.createCompany(createNewCompany);

        return new  CreateCompanyResult(
                savedCompany.getCompanyId().toString(),
                savedCompany.getCompanyName(),
                savedCompany.getCompanyIndustry(),
                savedCompany.getCompanyRating().toString()
        );
    }

    //update company
    @Override
    public UpdateCompanyResult updateCompany(UpdateCompanyCommand  updateCompanyCommand){

        //check company availability by custom method
        Company company = companyRepository.getCompanyById(updateCompanyCommand.companyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found" + " " + updateCompanyCommand.companyId()));

        //update through domain model
        company.updateCompany(
                updateCompanyCommand.companyName(),
                updateCompanyCommand.companyIndustry(),
                updateCompanyCommand.companyRating()
        );

        Company savedCompany = companyRepository.updateCompany(company);

        //set values to domain repo
        return new   UpdateCompanyResult(
                savedCompany.getCompanyId().toString(),
                savedCompany.getCompanyName(),
                savedCompany.getCompanyIndustry(),
                savedCompany.getCompanyRating().toString()
        );
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
