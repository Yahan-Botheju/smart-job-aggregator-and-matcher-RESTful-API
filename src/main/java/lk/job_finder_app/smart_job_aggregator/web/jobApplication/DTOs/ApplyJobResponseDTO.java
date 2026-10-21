package lk.job_finder_app.smart_job_aggregator.web.jobApplication.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyJobResponseDTO {
    private String companyId;
    private String companyName;
    private String companyIndustry;
    private String companyRating;

    private String jobApplicationId;
    private String resumeUrl;
    private double matchScore;
    private String applicationStatus;
    private String appliedAt;

    private String postId;
    private String postTitle;
    private String postDescription;
    private String postSalary;
    private String createdAt;
    private String jobStatus;
    private Set<String> skillsRequired;

    private String userId;
    private String userName;
    private String userEmail;
    private String role;
}
