package lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobRequestDTO {
    @NotBlank(message = "URL cannot be empty")
    private String resumeUrl;
    @NotNull(message = "user ID cannot be empty")
    private Long userId;
    @NotNull(message = "job post ID cannot be empty")
    private Long jobPostId;
}
