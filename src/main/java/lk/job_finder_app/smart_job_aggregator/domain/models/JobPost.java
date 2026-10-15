package lk.job_finder_app.smart_job_aggregator.domain.models;


import lk.job_finder_app.smart_job_aggregator.domain.models.enums.JobStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class JobPost {
    private Long postId;
    private String postTitle;
    private String postDescription;
    private BigDecimal postSalary;
    private LocalDate createdAt;
    private JobStatus jobStatus;
    private Set<String> skillsRequired;

    private Long companyId;

    public JobPost(Long postId, String postTitle, String postDescription, BigDecimal postSalary, LocalDate createdAt, JobStatus jobStatus, Set<String> skillsRequired, Long companyId) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.postSalary = postSalary;
        this.createdAt = createdAt;
        this.jobStatus = jobStatus;
        this.skillsRequired = skillsRequired;
        this.companyId = companyId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public BigDecimal getPostSalary() {
        return postSalary;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public Set<String> getSkillsRequired() {
        return skillsRequired;
    }

    public Long getCompanyId() {
        return companyId;
    }


    /* __FACTORY_METHOD__ */

    public static JobPost createJobPost(
            String postTitle,
            String postDescription,
            BigDecimal postSalary,
            LocalDate createdAt,
            JobStatus jobStatus,
            Set<String> skillsRequired,
            Long companyId
    ) {
        return new JobPost(
                null,
                postTitle,
                postDescription,
                postSalary,
                createdAt,
                jobStatus,
                skillsRequired,
                companyId
        );
    }

}
