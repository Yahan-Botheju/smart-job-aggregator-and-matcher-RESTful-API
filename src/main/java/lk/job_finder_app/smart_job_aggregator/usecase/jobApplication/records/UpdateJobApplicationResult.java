package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records;

public record UpdateJobApplicationResult(
        String jobApplicationId,
        String resumeUrl,
        double matchScore,
        String applicationStatus,
        String appliedAt,
        String userId,
        String jobPostId,
        String companyId
) {
}
