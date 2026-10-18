package lk.job_finder_app.smart_job_aggregator.domain.repositories;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface JobPostRepository {

    //job post find by id (CUSTOM METHOD)
    Optional<JobPost> getJobPostById(Long id);

    //find post by skill
    Optional<List<JobPost>> getJobPostsBySkills(Set<String> skills);

    //get all posts
    List<JobPost> getAllJobPosts();

    //create job post
    JobPost createJobPost(JobPost jobPost);

    //update job post
    JobPost updateJobPost(Long postId, JobPost jobPost);

    //delete job post
    void deleteJobPost(Long id);

    //create expire job post
    void expireOldJobPosts(LocalDate expiryLimit);

    //matching jobs
    List<JobPost> findJobsByMatchingSkills(Set<String> userSkills);

}
