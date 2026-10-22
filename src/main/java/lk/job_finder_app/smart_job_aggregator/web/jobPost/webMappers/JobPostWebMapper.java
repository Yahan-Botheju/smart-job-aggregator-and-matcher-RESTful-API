package lk.job_finder_app.smart_job_aggregator.web.jobPost.webMappers;

import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.*;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobPostWebMapper {

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
