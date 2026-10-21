package lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.records;

import java.util.Set;

public record GetAllJobApplicationsResults(
        String companyId,
        String companyName,
        String companyIndustry,
        String companyRating,

        String jobApplicationId,
        String resumeUrl,
        double matchScore,
        String applicationStatus,
        String appliedAt,

        String postId,
        String postTitle,
        String postDescription,
        String postSalary,
        String createdAt,
        String jobStatus,
        Set<String>skillsRequired,

        String userId,
        String userName,
        String userEmail,
        String role
) {
}
