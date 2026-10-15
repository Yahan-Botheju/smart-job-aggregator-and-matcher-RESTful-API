package lk.job_finder_app.smart_job_aggregator.web.jobPost.controllers;

import jakarta.validation.Valid;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.globalResponseHandler.StandardResponse;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.JobPostUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.GetAllPostsResult;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.GetAllPostsResponseDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.JobPostRequestDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.JobPostResponseDTO;
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
    public ResponseEntity<StandardResponse<JobPostResponseDTO>> createJobPost(
            @Valid @RequestBody JobPostRequestDTO jobPostRequestDTO
            ){
        //turn requestDTO to domain model
        JobPost toDomainModel = jobPostWebMapper.toDomainModel(jobPostRequestDTO);
        //set values to usecase (custom job post and company method)
        JobPostWithCompanyAggregate savedJobPost = jobPostUseCase.createJobPost(toDomainModel);
        //get response with company name id using custom mapper
        JobPostResponseDTO responseDTO = jobPostWebMapper.customResponseDTO(savedJobPost);

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
    public ResponseEntity<StandardResponse<JobPostResponseDTO>> updateJobPost(
            @PathVariable Long postId,
            @Valid @RequestBody JobPostRequestDTO jobPostRequestDTO
    ){
        JobPost toDomainModel = jobPostWebMapper.toDomainModel(jobPostRequestDTO);
        JobPostWithCompanyAggregate toUseCase = jobPostUseCase.updateJobPost(postId, toDomainModel);
        JobPostResponseDTO responseDTO = jobPostWebMapper.customResponseDTO(toUseCase);

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
