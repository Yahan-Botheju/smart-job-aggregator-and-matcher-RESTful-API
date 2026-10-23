package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.mapper;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.entity.JobPostEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobPostPersistenceMapper {

    //domain model to entity
    @Mapping(source = "companyId", target = "company.companyId")
    JobPostEntity toEntity(JobPost jobPost);

    //entity to domain model
    @Mapping(source = "company.companyId", target = "companyId")
    JobPost toDomainModel(JobPostEntity jobPostEntity);

    //set new data to existing entity
    JobPostEntity updateEntityWithNewData(JobPost jobPost, @MappingTarget JobPostEntity jobPostEntity);
}

