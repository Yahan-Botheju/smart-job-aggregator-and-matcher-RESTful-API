package lk.job_finder_app.smart_job_aggregator.infrastructure.configs;

import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobApplicationRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobPostRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.company.persistence.CompanyRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.company.persistence.jpa.JpaCompanyRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.company.persistence.mapper.CompanyPersistenceMapper;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobApplication.persistence.JobApplicationRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobApplication.persistence.jpa.JpaJobApplicationRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobApplication.persistence.mapper.JobApplicationPersistenceMapper;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobPost.persistence.JobPostRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobPost.persistence.jpa.JpaJobPostRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobPost.persistence.mapper.JobPostPersistenceMapper;
import lk.job_finder_app.smart_job_aggregator.infrastructure.role.persistence.jpa.JpaRoleRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.user.persistence.UserRepositoryImpl;
import lk.job_finder_app.smart_job_aggregator.infrastructure.user.persistence.jpa.JpaUserRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.user.persistence.mapper.UserPersistenceMapper;
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
