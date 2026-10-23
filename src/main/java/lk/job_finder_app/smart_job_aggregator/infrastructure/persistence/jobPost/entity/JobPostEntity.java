package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.JobStatus;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "jobPosts")
@SoftDelete(columnName = "is_deleted")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotEmpty(message = "Title cannot be empty")
    private String postTitle;

    @NotEmpty(message = "Description cannot be empty")
    private String postDescription;

    @NotNull(message = "Salary cannot be empty")
    private BigDecimal postSalary;

    @NotNull(message = "Date time cannot be empty")
    private LocalDate createdAt;


    @Enumerated(EnumType.STRING)
    private JobStatus jobStatus;

    @ElementCollection
    @CollectionTable(name = "job_post_skills", joinColumns = @JoinColumn(name = "postId"))
    private Set<String> skillsRequired;

    @ManyToOne
    @JoinColumn(name = "companyId",  nullable = false)
    private CompanyEntity company;

}
