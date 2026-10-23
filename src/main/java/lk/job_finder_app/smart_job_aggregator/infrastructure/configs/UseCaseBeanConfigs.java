package lk.job_finder_app.smart_job_aggregator.infrastructure.configs;

import lk.job_finder_app.smart_job_aggregator.domain.repositories.CompanyRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobApplicationRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobPostRepository;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.usecase.company.CompanyUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.company.CompanyUseCaseImpl;
import lk.job_finder_app.smart_job_aggregator.usecase.external_api.museAPI.client.TheMuseClient;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.JobApplicationUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.jobApplication.JobApplicationUseCaseImpl;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.JobPostUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.jobPost.JobPostUseCaseImpl;
import lk.job_finder_app.smart_job_aggregator.usecase.user.UserUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.user.UserUseCaseImpl;
import lk.job_finder_app.smart_job_aggregator.web.external_api.mappers.ExternalJobMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class UseCaseBeanConfigs {

    /* __USER_USE_CASE__ */

    @Bean
    UserUseCase userUseCase(
            UserRepository userRepository
    ) {
        return new UserUseCaseImpl(userRepository);
    }


    /* __COMPANY_USE_CASE__ */

    @Bean
    CompanyUseCase companyUseCase(CompanyRepository companyRepository) {
        return new CompanyUseCaseImpl(companyRepository);
    }


    /* __JOB_APPLICATION_USE_CASE__ */

    @Bean
    JobApplicationUseCase jobApplicationUseCase(
            JobApplicationRepository jobApplicationRepository,
            CompanyRepository companyRepository,
            JobPostRepository jobPostRepository,
            UserRepository userRepository
    ) {
        return new JobApplicationUseCaseImpl(
                jobApplicationRepository,
                companyRepository,
                jobPostRepository,
                userRepository
        );
    }


    /* __JOB_POST_USE_CASE__ */

    @Bean
    JobPostUseCase jobPostUseCase(
            JobPostRepository jobPostRepository,
            CompanyRepository companyRepository,
            UserRepository userRepository,
            TheMuseClient theMuseClient,
            ExternalJobMapper externalJobMapper
    ) {
        return new JobPostUseCaseImpl(jobPostRepository, companyRepository, userRepository,  theMuseClient, externalJobMapper);
    }


}
