package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.mapper;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplication;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.entity.JobApplicationEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobApplicationPersistenceMapper {

    //domain model to entity
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "jobPostId", target = "jobPost.postId")
    JobApplicationEntity toEntity(JobApplication jobApplication);

    //entity to domain model
    @Mapping(source = "jobPost.company.companyId", target = "companyId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "jobPost.postId", target = "jobPostId")
    JobApplication toDomainModel(JobApplicationEntity jobApplicationEntity);

    //update
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "jobPostId", target = "jobPost.postId")
    JobApplicationEntity toUpdateEntity(
            JobApplication jobApplication,
            @MappingTarget
            JobApplicationEntity jobApplicationEntity
    );
}
