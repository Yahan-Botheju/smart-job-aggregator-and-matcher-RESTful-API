package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records;

public record ApplyJobCommand(
        String resumeUrl,
        Long userId,
        Long jobPostId
) {
}
