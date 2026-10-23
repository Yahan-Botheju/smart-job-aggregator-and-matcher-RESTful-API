package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.ApplicationStatus;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.entity.JobPostEntity;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobApplications")
@SoftDelete(columnName = "is_deleted")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobApplicationId;
    @NotBlank(message = "URL cannot be empty")
    private String resumeUrl;
    @NotNull(message = "Matching Score cannot be empty")
    private double matchScore;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    @NotNull(message = "Date time cannot be empty")
    private LocalDateTime appliedAt;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "postId",  nullable = false)
    private JobPostEntity jobPost;
}
