package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.mapper;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.entity.CompanyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyPersistenceMapper {

    //domain model to entity
    CompanyEntity toEntity(Company company);

    //entity to domain model
    Company toDomainModel(CompanyEntity companyEntity);

    //set changed fields data to existing entity
    CompanyEntity updateEntityWithNewData(Company company, @MappingTarget CompanyEntity companyEntity);
}
