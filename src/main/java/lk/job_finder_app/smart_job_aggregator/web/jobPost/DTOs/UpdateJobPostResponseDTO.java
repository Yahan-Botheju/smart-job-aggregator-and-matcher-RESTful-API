package lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs;

import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobPostResponseDTO {
    private JobPost jobPost;
    private Company company;
}
