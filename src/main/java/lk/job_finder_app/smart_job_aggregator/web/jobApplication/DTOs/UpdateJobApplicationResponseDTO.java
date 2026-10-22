package lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobApplicationResponseDTO {
    private String jobApplicationId;
    private String resumeUrl;
    private double matchScore;
    private String applicationStatus;
    private String appliedAt;
    private String userId;
    private String jobPostId;
    private String companyId;
}
