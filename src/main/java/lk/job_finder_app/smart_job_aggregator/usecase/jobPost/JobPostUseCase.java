package lk.job_finder_app.smart_job_aggregator.usecase.jobPost;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPostWithCompanyAggregate;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.CreateJobPostCommand;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.CreateJobPostResult;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records.GetAllPostsResult;

import java.util.List;

public interface JobPostUseCase {
    //get all job posts
    List<GetAllPostsResult> getAllJobPosts();

    //create job post
    CreateJobPostResult createJobPost(CreateJobPostCommand createJobPostCommand);

    //update job post
    JobPostWithCompanyAggregate updateJobPost(Long postId, JobPost jobPost);

    //delete job post
    void deleteJobPost(Long postId);

    //job matching
    List<JobPostWithCompanyAggregate> getRecommendedJobsForUser(Long userId);

    //threading method
    public List<JobPostWithCompanyAggregate> getMultiSourceRecommendations(Long userId);
}
