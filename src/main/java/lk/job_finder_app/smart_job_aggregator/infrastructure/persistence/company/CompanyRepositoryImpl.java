package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.entity.CompanyEntity;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.jpa.JpaCompanyRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.mapper.CompanyPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    //inject jpa repo
    private final JpaCompanyRepository jpaCompanyRepository;

    //inject persistence mapper
    private final CompanyPersistenceMapper companyPersistenceMapper;

    //check company by ID
    @Override
    public Optional<Company> getCompanyById(long companyId) {
        return jpaCompanyRepository.findById(companyId)
                .map(companyPersistenceMapper::toDomainModel);

    }

    //get all companies
    @Override
    public List<Company> getAllCompanies(){
        //get all details from db
        List<CompanyEntity > companyEntities = jpaCompanyRepository.findAll();
        //turn entity to domain model and return
        return companyEntities.stream().map(companyPersistenceMapper::toDomainModel).toList();
    }

    //create company
    @Override
    public Company createCompany(Company company){
        //domain model to entity
        CompanyEntity companyEntity = companyPersistenceMapper.toEntity(company);
        //save in db
        CompanyEntity savedEntity = jpaCompanyRepository.save(companyEntity);
        //turn to domain model and return
        return companyPersistenceMapper.toDomainModel(savedEntity);
    }

    //update company
    @Override
    public Company updateCompany(Company company){

        //update existing entity
        CompanyEntity updatedCompanyEntity = companyPersistenceMapper.toEntity(company);

        //save in db
        jpaCompanyRepository.save(updatedCompanyEntity);

        return companyPersistenceMapper.toDomainModel(updatedCompanyEntity);
    }

    //delete company
    @Override
    public void deleteCompany(Long companyId){
        //check availability of company
        jpaCompanyRepository.findById(companyId)
                .orElseThrow(()-> new ResourceNotFoundException("Company Not Found"));
        //then delete
        jpaCompanyRepository.deleteById(companyId);
    }
}
