package lk.job_finder_app.smart_job_aggregator.usecase.jobPost.records;

import java.math.BigDecimal;
import java.util.Set;

public record CreateJobPostCommand(
        String postTitle,
        String postDescription,
        BigDecimal postSalary,
        Set<String> skillsRequired,
        Long companyId
) {
}
