package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplication;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobApplicationAggregate;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records.*;

import java.util.List;

public interface JobApplicationUseCase {
    //get all job applications
    List<GetAllJobApplicationsResults> getAllJobApplications();

    //create job application
    ApplyJobResult applyForJob(ApplyJobCommand applyJobCommand);

    //update job application
    UpdateJobApplicationResult updateJobApplication(UpdateJobApplicationCommand updateJobApplicationCommand);
}
