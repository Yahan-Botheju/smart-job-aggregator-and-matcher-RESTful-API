package lk.job_finder_app.smart_job_aggregator.web.jobApplication.webMappers;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplication;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplicationAggregate;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.ApplyJobCommand;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.ApplyJobResult;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.GetAllJobApplicationsResults;
import lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobApplicationWebMapper {

    //domain model to response
    JobApplicationResponseDTO toResponseDTO(JobApplication jobApplication);

    //request to domain model
    JobApplication toDomainModel(JobApplicationRequestDTO jobApplicationRequestDTO);

    //create aggregator mapper
    default  JobApplicationResponseDTO aggregatorResponseDTO(
            JobApplicationAggregate jobApplicationAggregate
    ){
        JobApplicationResponseDTO responseDTO = toResponseDTO(jobApplicationAggregate.getJobApplication());

        //get company name
        responseDTO.setCompanyName(jobApplicationAggregate.getCompany().getCompanyName());
        //get username
        responseDTO.setUserName(jobApplicationAggregate.getUser().getUserName());
        //get post title
        responseDTO.setPostTitle(jobApplicationAggregate.getJobPost().getPostTitle());

        return responseDTO;
    }

    /* __GET_ALL_JOB_APPLICATIONS__ */

    //to response
    GetAllJobApplicationsResponseDTO toGetAllJobApplicationsResponseDTO(GetAllJobApplicationsResults getAllJobApplicationsResults);


    /* __APPLY_JOB__ */

    //to command
    ApplyJobCommand toApplyJobCommand(ApplyJobRequestDTO applyJobRequestDTO);

    //to response
    ApplyJobResponseDTO toApplyJobResponseDTO(ApplyJobResult applyJobResult);
}
