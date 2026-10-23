package lk.job_finder_app.smart_job_aggregator.infrastructure.configs;

import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobApplicationRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobPostRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.CompanyRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.jpa.JpaCompanyRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.mapper.CompanyPersistenceMapper;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.JobApplicationRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.jpa.JpaJobApplicationRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobApplication.mapper.JobApplicationPersistenceMapper;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.JobPostRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.jpa.JpaJobPostRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.mapper.JobPostPersistenceMapper;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.role.jpa.JpaRoleRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.UserRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.jpa.JpaUserRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.mapper.UserPersistenceMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceBeanConfigs {

    /* __USER__ */

    @Bean
    UserRepository userRepository(
            JpaUserRepository jpaUserRepository,
            JpaRoleRepository jpaRoleRepository,
            UserPersistenceMapper userPersistenceMapper

    ) {
        return new UserRepositoryImpl(jpaUserRepository,userPersistenceMapper,jpaRoleRepository);
    }


    /* __COMPANY__ */

    @Bean
    CompanyRepository companyRepository(
            JpaCompanyRepository jpaCompanyRepository,
            CompanyPersistenceMapper companyPersistenceMapper
    ) {
        return new CompanyRepositoryImpl(jpaCompanyRepository, companyPersistenceMapper);
    }


    /* __JOB_APPLICATION__ */

    @Bean
    JobApplicationRepository jobApplicationRepository(
            JpaJobApplicationRepository jpaJobApplicationRepository,
            JobApplicationPersistenceMapper jobApplicationPersistenceMapper
    ) {
        return new JobApplicationRepositoryImpl(jpaJobApplicationRepository, jobApplicationPersistenceMapper);
    }

    /* __JOB_POST__ */

    @Bean
    JobPostRepository jobPostRepository(
            JpaJobPostRepository jobPostRepository,
            JobPostPersistenceMapper jobPostPersistenceMapper
    ) {
        return new JobPostRepositoryImpl(jobPostRepository, jobPostPersistenceMapper);
    }
}
