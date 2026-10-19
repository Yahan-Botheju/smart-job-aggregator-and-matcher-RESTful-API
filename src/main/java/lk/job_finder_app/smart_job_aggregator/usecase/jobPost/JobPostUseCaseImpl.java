package lk.job_finder_app.smart_job_aggregator.usecase.jobPost;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.JobStatus;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobPostRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.globalExceptionHandler.superClasses.MethodArgumentNotValidException;
import lk.job_finder_app.smart_job_aggregator.infrastructure.external_api.museAPI.DTOs.ExternalJobResponseDTO;
import lk.job_finder_app.smart_job_aggregator.infrastructure.external_api.museAPI.client.TheMuseClient;
import lk.job_finder_app.smart_job_aggregator.infrastructure.external_api.museAPI.mappers.ExternalJobMapper;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class JobPostUseCaseImpl implements JobPostUseCase {

    //inject job post repo
    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final TheMuseClient theMuseClient;
    private final ExternalJobMapper externalJobMapper;


    /* ----- HELPER METHODS ----- */

    //method for getting company details
    private Company getCompanyDetailsById(Long companyId) {
        return companyRepository.getCompanyById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company Not Found"));
    }

    //set corn job
    @Scheduled(cron = "0 * * * * *")
    public void executeJobPostExpire() {
        LocalDate expiryLimit = LocalDate.now().minusDays(1);
        jobPostRepository.expireOldJobPosts(expiryLimit);
    }


    //get skills of applier
    private List<MultiJobsRecommendedResult> getJobsBySkills(Set<String> userSkills) {
        if (userSkills == null || userSkills.isEmpty()) return List.of();
        //get matching jobs via custom query
        List<JobPost> matchedJobs = jobPostRepository.findJobsByMatchingSkills(userSkills);

        return matchedJobs.stream().map(job -> {
            //get company
            Company externalCompany = getCompanyDetailsById(job.getCompanyId());

            return new MultiJobsRecommendedResult(
                    job.getPostId().toString(),
                    job.getPostTitle(),
                    job.getPostDescription(),
                    job.getPostSalary().toPlainString(),
                    job.getCreatedAt().toString(),
                    job.getJobStatus().name(),
                    job.getSkillsRequired(),
                    null,
                    externalCompany.getCompanyName(),
                    externalCompany.getCompanyIndustry(),
                    null
            );
        }).toList();
    }


    /* ----- PUBLIC METHODS ----- */


    //get all job posts
    @Override
    public List<GetAllPostsResult> getAllJobPosts() {

        List<JobPost> jobPosts = jobPostRepository.getAllJobPosts();
        return jobPosts.stream()
                .map(jobPost -> new GetAllPostsResult(
                        jobPost.getPostId().toString(),
                        jobPost.getPostTitle(),
                        jobPost.getPostDescription(),
                        jobPost.getPostSalary().toString(),
                        jobPost.getCreatedAt().toString(),
                        jobPost.getJobStatus().toString(),
                        jobPost.getSkillsRequired(),
                        jobPost.getCompanyId().toString()
                )).toList();
    }

    //create job post
    @Override
    public CreateJobPostResult createJobPost(CreateJobPostCommand createJobPostCommand) {
        //check company availability using helper method
        Company company = getCompanyDetailsById(createJobPostCommand.companyId());

        LocalDate createdAt = LocalDate.now();

        JobPost newJobPost = JobPost.createJobPost(
                createJobPostCommand.postTitle(),
                createJobPostCommand.postDescription(),
                createJobPostCommand.postSalary(),
                createdAt,
                JobStatus.ACTIVE,
                createJobPostCommand.skillsRequired(),
                company.getCompanyId()
        );

        //save new job post in db
        JobPost savedJobPost = jobPostRepository.createJobPost(newJobPost);

        //return both results for response
        return new CreateJobPostResult(
                savedJobPost.getPostId().toString(),
                savedJobPost.getPostTitle(),
                savedJobPost.getPostDescription(),
                savedJobPost.getPostSalary().toString(),
                savedJobPost.getCreatedAt().toString(),
                savedJobPost.getJobStatus().toString(),
                savedJobPost.getSkillsRequired(),
                savedJobPost.getCompanyId().toString()
        );

    }

    //update job post
    @Override
    public UpdateJobPostResult updateJobPost(UpdateJobPostCommand updateJobPostCommand) {

        JobPost jobPost = jobPostRepository.getJobPostById(updateJobPostCommand.postId())
                .orElseThrow(() -> new ResourceNotFoundException("Job Post Not Found" + ", " + updateJobPostCommand.postId()));

        LocalDate updatedAt = LocalDate.now();

        //update through domain model
        jobPost.updateJobPost(
                updateJobPostCommand.postTitle(),
                updateJobPostCommand.postDescription(),
                updateJobPostCommand.postSalary(),
                updatedAt,
                JobStatus.ACTIVE,
                updateJobPostCommand.skillsRequired()
        );


        //set update domain model to repo for db
        JobPost savedJobPost = jobPostRepository.updateJobPost(updateJobPostCommand.postId(), jobPost);
        Company company = getCompanyDetailsById(savedJobPost.getCompanyId());

        return new UpdateJobPostResult(
                company.getCompanyId().toString(),
                company.getCompanyName(),
                company.getCompanyIndustry(),
                company.getCompanyRating().toString(),
                savedJobPost.getPostId().toString(),
                savedJobPost.getPostTitle(),
                savedJobPost.getPostDescription(),
                savedJobPost.getPostSalary().toString(),
                savedJobPost.getCreatedAt().toString(),
                savedJobPost.getJobStatus().toString(),
                savedJobPost.getSkillsRequired()
        );
    }

    //delete job post
    @Override
    public void deleteJobPost(DeleteJobPostCommand deleteJobPostCommand) {

        if (deleteJobPostCommand.postId() == null) {
            throw new MethodArgumentNotValidException("Required fields cannot be empty");
        }

        jobPostRepository.getJobPostById(deleteJobPostCommand.postId())
                .orElseThrow(() -> new ResourceNotFoundException("Job Post Not Found" + ", " + deleteJobPostCommand.postId()));

        jobPostRepository.deleteJobPost(deleteJobPostCommand.postId());
    }

    //job matching related to user
    @Override
    public List<GetUserRecommendedJobResult> getRecommendedJobsForUser(GetUserRecommendedJobCommand getUserRecommendedJobCommand) {

        if (getUserRecommendedJobCommand.userId() == null) {
            throw new MethodArgumentNotValidException("Required fields cannot be empty");
        }

        User user = userRepository.userFindById(getUserRecommendedJobCommand.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        Set<String> recommendedJobs = user.getSkillsRequired();

        return List.of(new GetUserRecommendedJobResult(
                user.getUserId().toString(),
                recommendedJobs
        ));
    }

    //get db and API data related to user skills
    @Override
    public List<MultiJobsRecommendedResult> getMultiSourceRecommendations(MultiJobsRecommendedCommand multiJobsRecommendedCommand) {

        if (multiJobsRecommendedCommand == null || multiJobsRecommendedCommand.userId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        //check user availability
        User user = userRepository.userFindById(multiJobsRecommendedCommand.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        //get user skills via custom query
        Set<String> userSkills = user.getSkillsRequired();


        //allocate the data that taken from local db
        CompletableFuture<List<MultiJobsRecommendedResult>>
                localJobsFuture = CompletableFuture.supplyAsync(() -> getJobsBySkills(userSkills));


        //external API jobs
        CompletableFuture<List<MultiJobsRecommendedResult>> externalJobsFuture =
                CompletableFuture.supplyAsync(() -> {
                    List<ExternalJobResponseDTO.TheMuseJob> museJobs = theMuseClient.fetchExternalJobs();

                    return museJobs.stream().map(theMuseJob -> {

                        JobPost posts = externalJobMapper.toDomain(theMuseJob);


                        Company externalCompany = Company.createCompany(theMuseJob.company().name(), null, null);


                        return new MultiJobsRecommendedResult(
                                posts.getPostId().toString(),
                                posts.getPostTitle(),
                                posts.getPostDescription(),
                                posts.getPostSalary().toPlainString(),
                                posts.getCreatedAt().toString(),
                                posts.getJobStatus().name(),
                                posts.getSkillsRequired(),
                                null,
                                externalCompany.getCompanyName(),
                                externalCompany.getCompanyIndustry(),
                                null
                        );
                    }).toList();
                });

        return localJobsFuture.thenCombine(externalJobsFuture,
                (local, external) -> {
                    List<MultiJobsRecommendedResult> all = new ArrayList<>(local);
                    all.addAll(external);
                    return all;
                }).join();
    }


}
