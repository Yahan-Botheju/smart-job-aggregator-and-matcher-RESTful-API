package lk.job_finder_app.smart_job_aggregator.domain.models;

import lk.job_finder_app.smart_job_aggregator.domain.models.enums.ApplicationStatus;
import lk.job_finder_app.smart_job_aggregator.globalExceptionHandler.superClasses.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public class JobApplication {
    private Long jobApplicationId;
    private String resumeUrl;
    private double matchScore;
    private ApplicationStatus applicationStatus;
    private LocalDateTime appliedAt;

    private Long userId;
    private Long jobPostId;
    private Long companyId;

    public JobApplication(Long jobApplicationId, String resumeUrl, double matchScore, ApplicationStatus applicationStatus, LocalDateTime appliedAt, Long userId, Long jobPostId, Long companyId) {
        this.jobApplicationId = jobApplicationId;
        this.resumeUrl = resumeUrl;
        this.matchScore = matchScore;
        this.applicationStatus = applicationStatus;
        this.appliedAt = appliedAt;
        this.userId = userId;
        this.jobPostId = jobPostId;
        this.companyId = companyId;
    }

    public Long getJobApplicationId() { return jobApplicationId; }
    public String getResumeUrl() { return resumeUrl; }
    public double getMatchScore() { return matchScore; }
    public ApplicationStatus getApplicationStatus() { return applicationStatus; }
    public LocalDateTime getAppliedAt() { return appliedAt; }
    public Long getUserId() { return userId; }
    public Long getJobPostId() { return jobPostId; }
    public Long getCompanyId() { return companyId; }

    /* __DOMAIN_LOGICS__ */

    //set company Id on application
    public void setCompanyIdOnPost(Long companyId, LocalDateTime currentTime) {
        if(companyId == null) {
            throw new MethodArgumentNotValidException("companyId is cannot be mepty");
        }
        this.companyId = companyId;
        this.appliedAt = currentTime;
    }


    //job application default status
    public void jobApplicationDefaultStatus() {
        if (this.applicationStatus == null) {
            this.applicationStatus = ApplicationStatus.PENDING;
        }
    }

    //create method for calculate matching score
    public void calculateMatchScore(
            Set<String> userSkills,
            Set<String> requiredJobSkills
    ) {
        if(userSkills == null || userSkills.isEmpty() ||requiredJobSkills == null || requiredJobSkills.isEmpty()) {
            this.matchScore = 0.0;
            return;
        }
        long matchCount = userSkills.stream().filter(requiredJobSkills::contains).count();
        this.matchScore = (((double)  matchCount) /  requiredJobSkills.size()) * 100;
    }
}
