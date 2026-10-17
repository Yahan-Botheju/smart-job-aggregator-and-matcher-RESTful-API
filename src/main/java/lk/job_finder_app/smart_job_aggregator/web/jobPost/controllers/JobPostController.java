package lk.job_finder_app.smart_job_aggregator.web.jobPost.controllers;

import jakarta.validation.Valid;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.globalResponseHandler.StandardResponse;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.JobPostUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.*;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.*;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.webMappers.JobPostWebMapper;
import lk.job_finder_app.smart_job_aggregator.web.security.Authorize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/job-posts")
public class JobPostController {

    //inject use case
    private final JobPostUseCase  jobPostUseCase;
    private final JobPostWebMapper jobPostWebMapper;

    //get all job posts
    @GetMapping
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<StandardResponse<List<GetAllPostsResponseDTO>>> getAllJobPosts() {

        List<GetAllPostsResult> allPostsResults = jobPostUseCase.getAllJobPosts();
        List<GetAllPostsResponseDTO> responseDTO = allPostsResults.stream()
                .map(jobPostWebMapper::toGetAllPostsResult).toList();

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Details fetched successfully",
                LocalDateTime.now(),
                responseDTO
        ));
    }

    //create new post
    @PostMapping
    @Authorize({RoleName.COMPANY_RECRUITER, RoleName.ADMIN})
    public ResponseEntity<StandardResponse<CreateJobPostResponseDTO>> createJobPost(
            @Valid @RequestBody CreateJobPostRequestDTO createJobPostRequestDTO
            ){
        CreateJobPostCommand toCommand = jobPostWebMapper.toCreateJobPostCommand(createJobPostRequestDTO);
        CreateJobPostResult toResult =  jobPostUseCase.createJobPost(toCommand);
        CreateJobPostResponseDTO responseDTO = jobPostWebMapper.toCreateJobPostResponseDTO(toResult);

        return ResponseEntity.created(URI.create("/api/v1/job-posts/"))
                .body(new StandardResponse<>(
                        201,
                        "Job Post created successfully" + " , " +  responseDTO.getPostId(),
                        LocalDateTime.now(),
                        responseDTO
                ));

    }

    //update job post
    @PutMapping("/{postId}")
    @Authorize({RoleName.COMPANY_RECRUITER, RoleName.ADMIN})
    public ResponseEntity<StandardResponse<UpdateJobPostResponseDTO>> updateJobPost(
            @PathVariable Long postId,
            @Valid @RequestBody UpdateJobPostRequestDTO updateJobPostRequestDTO
    ){
        UpdateJobPostCommand toCommand = jobPostWebMapper.toUpdateJobPostCommand(postId,  updateJobPostRequestDTO);
        UpdateJobPostResult toUseCase = jobPostUseCase.updateJobPost(toCommand);
        UpdateJobPostResponseDTO  responseDTO = jobPostWebMapper.toUpdateJobPostResponseDTO(toUseCase);

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Job Post updated successfully" + "," + responseDTO.getPostId(),
                LocalDateTime.now(),
                responseDTO
        ));
    }

    //delete job post
    @DeleteMapping("/{postId}")
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<Void> deleteJobPost(
            @PathVariable Long postId
    ){
        jobPostUseCase.deleteJobPost(postId);
        return ResponseEntity.noContent().build();
    }

    //job post matching
    @GetMapping("/{userId}/recommendations")
    @Authorize({RoleName.COMPANY_RECRUITER, RoleName.ADMIN})
    public ResponseEntity<StandardResponse<List<JobPostResponseDTO>>> getRecommendedJobPostsForUser(
            @PathVariable Long userId
    ){
        List<JobPostWithCompanyAggregate> jobPosts = jobPostUseCase.getRecommendedJobsForUser(userId);
        List<JobPostResponseDTO> responseDTO = jobPosts.stream().map(jobPostWebMapper::customResponseDTO).toList();

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Details fetched successfully",
                LocalDateTime.now(),
                responseDTO
        ));
    }

}
