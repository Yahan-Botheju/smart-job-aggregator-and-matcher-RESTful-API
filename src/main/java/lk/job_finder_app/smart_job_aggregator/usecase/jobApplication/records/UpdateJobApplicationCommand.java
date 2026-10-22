package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records;

public record UpdateJobApplicationCommand(
        Long jobApplicationId,
        String resumeUrl,
        Long userId,
        Long jobPostId
) {
}
