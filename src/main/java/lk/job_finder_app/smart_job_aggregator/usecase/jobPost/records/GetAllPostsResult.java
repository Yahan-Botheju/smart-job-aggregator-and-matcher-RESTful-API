package lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records;

import java.util.Set;

public record GetAllPostsResult(
        String postId,
        String postTitle,
        String postDescription,
        String postSalary,
        String createdAt,
        String jobStatus,
        Set<String> skillsRequired,
        String companyId
) {
}
