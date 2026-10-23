package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.jpa;

import jakarta.transaction.Transactional;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.jobPost.entity.JobPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface JpaJobPostRepository extends JpaRepository<JobPostEntity, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE JobPostEntity j SET j.jobStatus = 'EXPIRED' " +
            "WHERE j.jobStatus = 'ACTIVE' AND j.createdAt < :expiryLimit")
    void expiredJobPosts(@Param("expiryLimit") LocalDate expiryLimit);



    @Query("SELECT DISTINCT j FROM JobPostEntity j " +
            "LEFT JOIN FETCH j.skillsRequired " +
            "WHERE EXISTS (SELECT s FROM j.skillsRequired s WHERE s IN :userSkills)")
    List<JobPostEntity> findJobsByMatchingSkills(@Param("userSkills") Set<String> userSkills);


}
