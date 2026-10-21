package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplication;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplicationAggregate;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.ApplyJobCommand;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.ApplyJobResult;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.GetAllJobApplicationsResults;

import java.util.List;

public interface JobApplicationUseCase {
    //get all job applications
    List<GetAllJobApplicationsResults> getAllJobApplications();

    //create job application
    ApplyJobResult applyForJob(ApplyJobCommand applyJobCommand);

    //update job application
    JobApplicationAggregate updateJobApplication(Long jobApplicationId, JobApplication jobApplication);
}
