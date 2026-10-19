package lk.job_finder_app.smart_job_aggregator.usecase.user.records;

import java.util.Set;

public record GetAllUsersResults(
        String userId,
        String userName,
        String userEmail,
        String role,
        Set<String>skillsRequired
) {
}
