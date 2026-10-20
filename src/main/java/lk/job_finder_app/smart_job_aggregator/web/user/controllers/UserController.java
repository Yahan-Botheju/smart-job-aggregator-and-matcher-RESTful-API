package lk.job_finder_app.smart_job_aggregator.web.user.controllers;

import jakarta.validation.Valid;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.globalResponseHandler.StandardResponse;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.JobPostUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.user.UserUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.*;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs.JobPostResponseDTO;
import lk.job_finder_app.smart_job_aggregator.web.jobPost.webMappers.JobPostWebMapper;
import lk.job_finder_app.smart_job_aggregator.web.security.Authorize;
import lk.job_finder_app.smart_job_aggregator.web.user.DTOs.*;
import lk.job_finder_app.smart_job_aggregator.web.user.webMappers.UserWebMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    //inject required dependencies
    private final UserUseCase userUseCase;
    private final JobPostUseCase jobPostUseCase;
    private final UserWebMapper userWebMapper;
    private final JobPostWebMapper jobPostWebMapper;

    //get all users
    @GetMapping
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<StandardResponse<List<GetAllUsersResponseDTO>>> getAllUsers(){

        List<GetAllUsersResults> usersDomainModel = userUseCase.getAllUsers();
        List<GetAllUsersResponseDTO> toResponseDTO = usersDomainModel.stream()
                .map(userWebMapper::getAllUsers).toList();

        return ResponseEntity.ok(
                new StandardResponse<>(
                        200,
                        "users data fetched successfully",
                        LocalDateTime.now(),
                        toResponseDTO
                )
        );

    }
    //create user
    @PostMapping
    @Authorize({RoleName.USER,  RoleName.ADMIN})
    public ResponseEntity<StandardResponse<CreateUserResponseDTO>> createUser(
            @Valid @RequestBody CreateUserRequestDTO createUserRequestDTO
    ){

        CreateUserCommand toCommand = userWebMapper.toCreateUserCommand(createUserRequestDTO);
        CreateUserResult toResult = userUseCase.createUser(toCommand);
        CreateUserResponseDTO toResponseDTO = userWebMapper.toCreateUserResponseDTO(toResult);

        return  ResponseEntity.created(URI.create("/api/v1/users/" + "," + toResponseDTO.getUserId())).body(
                new StandardResponse<>(
                        201,
                        "user created successfully",
                        LocalDateTime.now(),
                        toResponseDTO
                )
        );

    }
    //update user
    @PutMapping("/{userId}")
    @Authorize({RoleName.USER, RoleName.ADMIN})
    public ResponseEntity<StandardResponse<UpdateUserResponseDTO>> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO
    ){
        UpdateUserCommand toCommand = userWebMapper.toUpdateUserCommand(userId, updateUserRequestDTO);
        UpdateUserResult toResult = userUseCase.updateUser(toCommand);
        UpdateUserResponseDTO toResponseDTO = userWebMapper.toUpdateUserResponseDTO(toResult);

        return  ResponseEntity.ok(
                new StandardResponse<>(
                        200,
                        "user updated successfully",
                        LocalDateTime.now(),
                        toResponseDTO
                )
        );

    }
    //delete user
    @DeleteMapping("/{userId}")
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ){
        userUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    //use job post matching method
    @GetMapping("/{userId}/recommendations")
    @Authorize({RoleName.USER, RoleName.ADMIN})
    public ResponseEntity<StandardResponse<List<JobPostResponseDTO>>> getRecommendedJobPostsForUser(
            @PathVariable Long userId
    ){
        List<JobPostWithCompanyAggregate> jobPosts = jobPostUseCase.getRecommendedJobsForUser(userId);
        List<JobPostResponseDTO> responseDTO = jobPosts.stream().map(jobPostWebMapper::customResponseDTO).toList();

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Details fetched successfully" + " , " + userId,
                LocalDateTime.now(),
                responseDTO
        ));
    }

    //get multisource data related to use skills
    @GetMapping("recommendations/{userId}/multi-source")
    @Authorize({RoleName.USER,  RoleName.ADMIN})
    public ResponseEntity<StandardResponse<List<JobPostResponseDTO>>> getMultiSourceRecommendations(
            @PathVariable Long userId
    ){
        List<JobPostWithCompanyAggregate> threadJobPost = jobPostUseCase.getMultiSourceRecommendations(userId);
        List<JobPostResponseDTO> responseDTO = threadJobPost.stream().map(jobPostWebMapper::customResponseDTO).toList();

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Details fetched successfully" + " , " + userId,
                LocalDateTime.now(),
                responseDTO
        ));


    }

}
