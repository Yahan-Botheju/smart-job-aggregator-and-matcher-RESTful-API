package lk.job_finder_app.smart_job_aggregator.usecase.user.records;

import java.util.Set;

public record UpdateUserCommand(
        Long userId,
        String userName,
        String userEmail,
        Set<String>skillsRequired
) {
    public UpdateUserCommand {
        if(userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        if (userName == null || userName.isBlank()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }

        if (userEmail == null || userEmail.isBlank()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        if (skillsRequired == null || skillsRequired.isEmpty()) {
            throw new IllegalArgumentException("At least one skill is required");
        }

        skillsRequired = Set.copyOf(skillsRequired);
    }
}
