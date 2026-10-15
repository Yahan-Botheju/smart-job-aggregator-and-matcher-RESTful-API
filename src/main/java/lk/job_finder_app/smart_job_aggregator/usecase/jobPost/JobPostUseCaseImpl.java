package lk.job_finder_app.smart_job_aggregator.usecase.jobPost;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.JobStatus;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobPostRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.external_api.museAPI.DTOs.ExternalJobResponseDTO;
import lk.job_finder_app.smart_job_aggregator.infrastructure.external_api.museAPI.client.TheMuseClient;
import lk.job_finder_app.smart_job_aggregator.infrastructure.external_api.museAPI.mappers.ExternalJobMapper;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.GetAllCompaniesResult;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.CreateJobPostCommand;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.CreateJobPostResult;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.GetAllPostsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class JobPostUseCaseImpl implements JobPostUseCase{

    //inject job post repo
    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final TheMuseClient theMuseClient;
    private final ExternalJobMapper externalJobMapper;


    /* ----- HELPER METHODS ----- */

    //method for getting company details
    private Company getCompanyDetailsById(Long companyId){
        return companyRepository.getCompanyById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company Not Found"));
    }

    //set corn job
    @Scheduled(cron = "0 * * * * *")
    public void executeJobPostExpire(){
        LocalDate expiryLimit = LocalDate.now().minusDays(1);
        jobPostRepository.expireOldJobPosts(expiryLimit);
    }


    //get skills of applier
    private List<JobPostWithCompanyAggregate> getJobsBySkills(Set<String> userSkills) {
        if (userSkills == null || userSkills.isEmpty()) return List.of();
        //get matching jobs via custom query
        List<JobPost> matchedJobs = jobPostRepository.findJobsByMatchingSkills(userSkills);

        return matchedJobs.stream().map(job -> {
            Company company = getCompanyDetailsById(job.getCompanyId());
            return new JobPostWithCompanyAggregate(job, company);
        }).toList();
    }


    /* ----- PUBLIC METHODS ----- */


    //get all job posts
    @Override
    public List<GetAllPostsResult> getAllJobPosts(){

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
    public CreateJobPostResult createJobPost(CreateJobPostCommand createJobPostCommand){
        //check company availability using helper method
        Company company = getCompanyDetailsById(createJobPostCommand.companyId());

        LocalDate createdAt = LocalDateTime.now();

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
    public JobPostWithCompanyAggregate updateJobPost(
            Long postId,
            JobPost jobPost
    ){
       jobPostRepository.getJobPostById(postId)
               .orElseThrow(() -> new ResourceNotFoundException("Job Post Not Found" + ", " +  postId));

       jobPost.setDefaultJobStatus();
       //set update domain model to repo for db
       JobPost savedJobPost = jobPostRepository.updateJobPost(postId, jobPost);
       Company company = getCompanyDetailsById(savedJobPost.getCompanyId());

       return new JobPostWithCompanyAggregate(savedJobPost, company);
    }

    //delete job post
    @Override
    public void deleteJobPost(Long postId){
        jobPostRepository.getJobPostById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Post Not Found" + ", " +  postId));

        jobPostRepository.deleteJobPost(postId);
    }

    //job matching related to user
    @Override
    public List<JobPostWithCompanyAggregate> getRecommendedJobsForUser(Long userId){
        User user = userRepository.userFindById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        return getJobsBySkills(user.getSkillsRequired());
    }

    //get db and API data related to user skills
    public List<JobPostWithCompanyAggregate> getMultiSourceRecommendations(
            Long userId
    ){
        //check user availability
        User user = userRepository.userFindById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        //get user skills via custom query
        Set<String> userSkills = user.getSkillsRequired();


        //allocate the data that taken from local db
        CompletableFuture<List<JobPostWithCompanyAggregate>>
                localJobsFuture = CompletableFuture.supplyAsync(() -> {
                    return getJobsBySkills(userSkills);
        });

        //external API jobs
        CompletableFuture<List<JobPostWithCompanyAggregate>>
                externalJobsFuture = CompletableFuture.supplyAsync(() -> {
            List<ExternalJobResponseDTO.TheMuseJob> museJobs = theMuseClient.fetchExternalJobs();

            return museJobs.stream().map( theMuseJob -> {
                JobPost posts = externalJobMapper.toDomain(theMuseJob);

                Company externalCompany = new Company();
                externalCompany.setCompanyName(theMuseJob.company().name());

                return new JobPostWithCompanyAggregate(posts, externalCompany);
            }).toList();
        });

        return localJobsFuture.thenCombine(externalJobsFuture,
                (local, external) -> {
            List<JobPostWithCompanyAggregate> all = new ArrayList<>(local);
            all.addAll(external);
            return all;
        }).join();
    }


}
