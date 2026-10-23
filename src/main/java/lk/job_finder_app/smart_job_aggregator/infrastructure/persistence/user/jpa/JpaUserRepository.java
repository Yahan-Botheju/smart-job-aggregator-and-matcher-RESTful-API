package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.jpa;

import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    //create custom query for keep lazy loading
    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.skillsRequired WHERE u.userId = :id")
    Optional<UserEntity> findByIdWithSkills(@Param("id") Long id);

    //user find by email
    Optional<UserEntity> findByEmail(String email);
}
