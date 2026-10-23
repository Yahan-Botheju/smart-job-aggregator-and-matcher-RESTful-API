package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.jpa;

import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.entity.JobApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaJobApplicationRepository extends JpaRepository<JobApplicationEntity, Long> {
}
