package lk.job_finder_app.smart_job_aggregator.web.jobApplication.webMappers;

import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.*;
import lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobApplicationWebMapper {

    /* __GET_ALL_JOB_APPLICATIONS__ */

    //to response
    GetAllJobApplicationsResponseDTO toGetAllJobApplicationsResponseDTO(GetAllJobApplicationsResults getAllJobApplicationsResults);


    /* __APPLY_JOB__ */

    //to command
    ApplyJobCommand toApplyJobCommand(ApplyJobRequestDTO applyJobRequestDTO);

    //to response
    ApplyJobResponseDTO toApplyJobResponseDTO(ApplyJobResult applyJobResult);

    /* __UPDATE_JOB_APPLICATION__ */

    //to command
    UpdateJobApplicationCommand toUpdateJobApplicationCommand(Long jobApplicationId, UpdateJobApplicationRequestDTO updateJobApplicationRequestDTO);

    //to response
    UpdateJobApplicationResponseDTO toUpdateJobApplicationResponseDTO(UpdateJobApplicationResult updateJobApplicationResult);
}
