package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.jpa;

import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
