package lk.job_finder_app.smart_job_aggregator.domain.models;


public class JobPostWithCompanyAggregate {
    private JobPost  jobPost;
    private Company  company;

    public JobPostWithCompanyAggregate(JobPost jobPost, Company company) {
        this.jobPost = jobPost;
        this.company = company;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public Company getCompany() {
        return company;
    }
}
