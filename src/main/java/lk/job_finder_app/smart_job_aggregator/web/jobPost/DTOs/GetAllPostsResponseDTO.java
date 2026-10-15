package lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllPostsResponseDTO {
    private String postId;
    private String postTitle;
    private String postDescription;
    private String postSalary;
    private String createdAt;
    private String jobStatus;
    private Set<String> skillsRequired;
    private String companyId;
}
