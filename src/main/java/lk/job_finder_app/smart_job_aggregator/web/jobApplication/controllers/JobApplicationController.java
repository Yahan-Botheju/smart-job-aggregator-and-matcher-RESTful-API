package lk.job_finder_app.smart_job_aggregator.web.jobApplication.controllers;

import jakarta.validation.Valid;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplication;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplicationAggregate;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.globalResponseHandler.StandardResponse;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.JobApplicationUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.GetAllJobApplicationsResults;
import lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs.GetAllJobApplicationsResponseDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs.JobApplicationRequestDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs.JobApplicationResponseDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobApplication.webMappers.JobApplicationWebMapper;
import lk.job_finder_app.smart_job_aggregator.web.security.Authorize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/job-applications")
@RequiredArgsConstructor
public class JobApplicationController {

    //inject required dependencies
    private final JobApplicationWebMapper  jobApplicationWebMapper;
    private final JobApplicationUseCase jobApplicationUseCase;


    //get all job applications
    @GetMapping
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<StandardResponse<List<GetAllJobApplicationsResponseDTO>>> getAllJobApplications(){

        List<GetAllJobApplicationsResults> jobApplications = jobApplicationUseCase.getAllJobApplications();
        List<GetAllJobApplicationsResponseDTO> responseDTOS = jobApplications.stream()
                .map(jobApplicationWebMapper::toGetAllJobApplicationsResponseDTO).toList();

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Details fetched successfully",
                LocalDateTime.now(),
                responseDTOS
        ));
    }

    //create job application
    @PostMapping("/apply")
    @Authorize({RoleName.ADMIN, RoleName.USER})
    public ResponseEntity<StandardResponse<JobApplicationResponseDTO>> applyForJob(
            @Valid  @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO
    ){
        JobApplication toDomainModel = jobApplicationWebMapper.toDomainModel(jobApplicationRequestDTO);
        JobApplicationAggregate jobApplicationAggregate = jobApplicationUseCase.applyForJob(toDomainModel);
        JobApplicationResponseDTO responseDTO = jobApplicationWebMapper.aggregatorResponseDTO(jobApplicationAggregate);

        return ResponseEntity.created(URI.create("/api/v1/job-applications/" + responseDTO.getJobApplicationId()))
                .body(new StandardResponse<>(
                        201,
                        "Job application created",
                        LocalDateTime.now(),
                        responseDTO
                ));
    }

    //update job application
    @PutMapping("/{jobApplicationId}")
    @Authorize({RoleName.ADMIN, RoleName.USER})
    public ResponseEntity<StandardResponse<JobApplicationResponseDTO>> updateJobApplication(
            @PathVariable Long jobApplicationId,
            @Valid @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO
    ){
        JobApplication toDomainModel = jobApplicationWebMapper.toDomainModel(jobApplicationRequestDTO);
        JobApplicationAggregate jobApplicationAggregate = jobApplicationUseCase.updateJobApplication(jobApplicationId, toDomainModel);
        JobApplicationResponseDTO responseDTO = jobApplicationWebMapper.aggregatorResponseDTO(jobApplicationAggregate);

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Application updated successfully",
                LocalDateTime.now(),
                responseDTO
        ));
    }



}
