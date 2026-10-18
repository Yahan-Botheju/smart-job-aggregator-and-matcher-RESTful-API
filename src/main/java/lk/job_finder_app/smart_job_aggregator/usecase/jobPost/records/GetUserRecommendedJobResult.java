package lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records;

import java.util.Set;

public record GetUserRecommendedJobResult(
        String userId,
        Set<String> recommendedJobs
) {
}
