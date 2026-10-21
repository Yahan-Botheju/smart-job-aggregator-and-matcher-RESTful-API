package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplication;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplicationAggregate;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.GetAllJobApplicationsResults;

import java.util.List;

public interface JobApplicationUseCase {
    //get all job applications
    List<GetAllJobApplicationsResults> getAllJobApplications();

    //create job application
    JobApplicationAggregate applyForJob(JobApplication jobApplication);

    //update job application
    JobApplicationAggregate updateJobApplication(Long jobApplicationId, JobApplication jobApplication);
}
