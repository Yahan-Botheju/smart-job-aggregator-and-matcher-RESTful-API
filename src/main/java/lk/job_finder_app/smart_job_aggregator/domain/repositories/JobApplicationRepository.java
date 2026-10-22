package lk.job_finder_app.smart_job_aggregator.domain.repositories;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplication;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository {
    //helper methods
    Optional<JobApplication> jobApplicationFindById(long jobApplicationId);

    //get all job applications
    List<JobApplication> getAllJobApplications();

    //create job application
    JobApplication applyForJob(JobApplication jobApplication);

    //update job application
    JobApplication updateJobApplication(JobApplication jobApplication);
}
