package lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserRecommendedJobsResponseDTO {
    private String userId;
    private Set<String> recommendedJobs;
}
