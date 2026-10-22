package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication;

import lk.job_finder_app.smart_job_aggregator.domain.models.*;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.ApplicationStatus;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobApplicationRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobPostRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.globalExceptionHandler.superClasses.ResourceNotFoundException;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.*;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class JobApplicationUseCaseImpl implements JobApplicationUseCase {

    //inject job application repo
    private final JobApplicationRepository jobApplicationRepository;

    //inject repositories (COMPANY, JOB-POST, USER)
    private final CompanyRepository companyRepository;
    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;

    /* ----- HELPER METHODS ----- */

    //create methods for getting details (COMPANY, JOB-POST, USER)

    private Company getCompanyDetailsById(Long companyId){
        return companyRepository.getCompanyById(companyId)
                .orElseThrow(() -> new RuntimeException("Company Not Found" + " , " +  companyId));
    }

    private JobPost getJobPostDetailsById(Long postId){
        return jobPostRepository.getJobPostById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Post not found" + " , " + postId));
    }

    private User getUserDetailsById(Long userId){
        return userRepository.userFindById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found" + " , " + userId));
    }

    /* ----- PUBLIC METHODS ----- */

    //get all job applications
    @Override
    public List<GetAllJobApplicationsResults> getAllJobApplications(){
        return jobApplicationRepository.getAllJobApplications().stream()
                .map(jobApplication -> {
                    Company company = getCompanyDetailsById(jobApplication.getCompanyId());
                    JobPost jobPost = getJobPostDetailsById(jobApplication.getJobPostId());
                    User user = getUserDetailsById(jobApplication.getUserId());

                    return new GetAllJobApplicationsResults(
                            company.getCompanyId().toString(),
                            company.getCompanyName(),
                            company.getCompanyIndustry(),
                            company.getCompanyRating().toString(),

                            jobApplication.getJobApplicationId().toString(),
                            jobApplication.getResumeUrl(),
                            jobApplication.getMatchScore(),
                            jobApplication.getApplicationStatus().toString(),
                            jobApplication.getAppliedAt().toString(),

                            jobPost.getPostId().toString(),
                            jobPost.getPostTitle(),
                            jobPost.getPostDescription(),
                            jobPost.getPostSalary().toString(),
                            jobPost.getCreatedAt().toString(),
                            jobPost.getJobStatus().toString(),
                            jobPost.getSkillsRequired(),

                            user.getUserId().toString(),
                            user.getUserName(),
                            user.getUserEmail(),
                            user.getRole().toString()

                    );
                }).toList();
    }

    //create job application
    @Override
    public ApplyJobResult applyForJob(ApplyJobCommand applyJobCommand){

        //validate using helper methods (JOB-POST, COMPANY, USER)
        JobPost jobPost = getJobPostDetailsById(applyJobCommand.jobPostId());
        Company company = getCompanyDetailsById(jobPost.getCompanyId());
        User user = getUserDetailsById(applyJobCommand.userId());

        LocalDateTime currentTime = LocalDateTime.now();

        //create new application through domain
        JobApplication createNewJobApplication = JobApplication.createNewJobApplication(
                applyJobCommand.resumeUrl(),
                0.0,
                ApplicationStatus.PENDING,
                currentTime,
                user.getUserId(),
                jobPost.getPostId(),
                company.getCompanyId()
        );
        //calculate matching score
        createNewJobApplication.calculateMatchScore(
                user.getSkillsRequired(),
                jobPost.getSkillsRequired()
        );

        JobApplication savedJobApplication = jobApplicationRepository.applyForJob(createNewJobApplication);

        return new ApplyJobResult(
                savedJobApplication.getCompanyId().toString(),
                company.getCompanyName(),
                company.getCompanyIndustry(),
                company.getCompanyRating().toString(),

                savedJobApplication.getJobApplicationId().toString(),
                savedJobApplication.getResumeUrl(),
                savedJobApplication.getMatchScore(),
                savedJobApplication.getApplicationStatus().toString(),
                savedJobApplication.getAppliedAt().toString(),

                savedJobApplication.getJobPostId().toString(),
                jobPost.getPostTitle(),
                jobPost.getPostDescription(),
                jobPost.getPostSalary().toString(),
                jobPost.getCreatedAt().toString(),
                jobPost.getJobStatus().toString(),
                jobPost.getSkillsRequired(),

                savedJobApplication.getUserId().toString(),
                user.getUserName(),
                user.getUserEmail(),
                user.getRole().toString()
        );
    }

    //update job application
    @Override
    public UpdateJobApplicationResult updateJobApplication(UpdateJobApplicationCommand updateJobApplicationCommand){

        JobApplication jobApplication = jobApplicationRepository
                .jobApplicationFindById(updateJobApplicationCommand.jobApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Job Application Not Found" + " , " + updateJobApplicationCommand.jobApplicationId()));

        //update through domain model
        jobApplication.updateJobApplication(
                jobApplication.getJobApplicationId(),
                updateJobApplicationCommand.resumeUrl(),
                jobApplication.getMatchScore(),
                jobApplication.getApplicationStatus(),
                jobApplication.getAppliedAt(),
                updateJobApplicationCommand.userId(),
                updateJobApplicationCommand.jobPostId(),
                jobApplication.getCompanyId()
        );

        JobApplication savedJobApplication = jobApplicationRepository
                .updateJobApplication(jobApplication);

        return new UpdateJobApplicationResult(
                savedJobApplication.getJobApplicationId().toString(),
                savedJobApplication.getResumeUrl(),
                savedJobApplication.getMatchScore(),
                savedJobApplication.getApplicationStatus().toString(),
                savedJobApplication.getAppliedAt().toString(),
                savedJobApplication.getUserId().toString(),
                savedJobApplication.getJobPostId().toString(),
                savedJobApplication.getCompanyId().toString()
        );
    }

}

