package lk.job_finder_app.smart_job_aggregator.infrastructure.configs;

import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.usecase.user.UserUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.user.UserUseCaseImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class UseCaseBeanConfigs {
    @Bean
    UserUseCase userUseCase(
            UserRepository userRepository
    ) {
        return new UserUseCaseImpl(userRepository);
    }
}
