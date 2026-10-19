package lk.job_finder_app.smart_job_aggregator.web.jobPost.webMappers;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.*;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobPostWebMapper {

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

    /* __CREATE_JOB_POSTS__ */

    //to command
    CreateJobPostCommand toCreateJobPostCommand(CreateJobPostRequestDTO createJobPostRequestDTO);

    //to response
    CreateJobPostResponseDTO toCreateJobPostResponseDTO(CreateJobPostResult  createJobPostResult);

    /* __UPDATE_JOB_POST__ */

    //to command
    UpdateJobPostCommand toUpdateJobPostCommand(Long postId, UpdateJobPostRequestDTO  updateJobPostRequestDTO);

    //to response
    UpdateJobPostResponseDTO  toUpdateJobPostResponseDTO(UpdateJobPostResult  updateJobPostResult);

    /*__DELETE_JOB_POST__ */

    //to command
    DeleteJobPostCommand toDeleteJobPostCommand(Long postId);

    /* __GET_RECOMMENDED_JOBS__ */

    //to command
    GetUserRecommendedJobCommand toGetUserRecommendedJobCommand(Long userId);

    //to response
    GetUserRecommendedJobsResponseDTO toGetUserRecommendedJobsResponseDTO(GetUserRecommendedJobResult  getUserRecommendedJobResult);
}
