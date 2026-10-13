package lk.job_finder_app.smart_job_aggregator.domain.models;


public class JobApplicationAggregate {
    private Company company;
    private JobApplication jobApplication;
    private JobPost jobPost;
    private User user;

    public JobApplicationAggregate(Company company, JobApplication jobApplication, JobPost jobPost, User user) {
        this.company = company;
        this.jobApplication = jobApplication;
        this.jobPost = jobPost;
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public JobApplication getJobApplication() {
        return jobApplication;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public User getUser() {
        return user;
    }
}
