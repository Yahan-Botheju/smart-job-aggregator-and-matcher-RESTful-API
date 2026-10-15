package lk.job_finder_app.smart_job_aggregator.web.jobPost.webMappers;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.GetAllPostsResult;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.GetAllPostsResponseDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.JobPostRequestDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.JobPostResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobPostWebMapper {

    //domain model to responseDTO
    JobPostResponseDTO toResponseDTO(JobPost jobPost);

    //requestDTO to domain model
    JobPost toDomainModel(JobPostRequestDTO jobPostRequestDTO);

    //create custom responseDTO for showing customer id and name
    @Mapping(source = "jobPost.postId", target = "postId")
    @Mapping(source = "jobPost.postTitle", target = "postTitle")
    @Mapping(source = "jobPost.postDescription", target = "postDescription")
    @Mapping(source = "jobPost.postSalary", target = "postSalary")
    @Mapping(source = "jobPost.jobStatus", target = "jobStatus")
    @Mapping(source = "jobPost.createdAt", target = "createdAt")
    @Mapping(source = "jobPost.skillsRequired", target = "skillsRequired")
    @Mapping(source = "company.companyId", target = "companyId")
    @Mapping(source = "company.companyName", target = "companyName")
    JobPostResponseDTO customResponseDTO(JobPostWithCompanyAggregate jobPostWithCompanyAggregate);

    /* __GET_ALL_POSTS__ */

    GetAllPostsResponseDTO toGetAllPostsResult(GetAllPostsResult getAllPostsResult);
}
