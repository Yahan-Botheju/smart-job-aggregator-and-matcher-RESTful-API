package lk.job_finder_app.smart_job_aggregator.infrastructure.jobPost.persistence;

import lk.job_finder_app.smart_job_aggregator.domain.models.JobPost;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.JobPostRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobPost.persistence.entity.JobPostEntity;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobPost.persistence.jpa.JpaJobPostRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.jobPost.persistence.mapper.JobPostPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class JobPostRepositoryImpl implements JobPostRepository {

    //inject jpa repo
    private final JpaJobPostRepository jpaJobPostRepository;

    //inject persistence mapper
    private final JobPostPersistenceMapper jobPostPersistenceMapper;

    /* ----- HELPER METHODS ----- */


    //job post find by id (CUSTOM METHOD)
    public Optional<JobPost> getJobPostById(Long postId){
        return jpaJobPostRepository.findById(postId).map(jobPostPersistenceMapper::toDomainModel);
    }

    @Override
    public Optional<List<JobPost>> getJobPostsBySkills(Set<String> skills) {
        return Optional.empty();
    }

    //save job post status expired in db
    @Override
    public void expireOldJobPosts(LocalDate expiryLimit) {
        jpaJobPostRepository.expiredJobPosts(expiryLimit);
    }

    //get matching jobs from custom query
    @Override
    public List<JobPost> findJobsByMatchingSkills(
            Set<String> userSkills
    ){
        List<JobPostEntity> entities = jpaJobPostRepository.findJobsByMatchingSkills(userSkills);
        return entities.stream().map(jobPostPersistenceMapper::toDomainModel).toList();
    }



    /* ----- PUBLIC METHODS ----- */

    //get all job posts
    @Override
    public List<JobPost> getAllJobPosts(){
        List<JobPostEntity> jobPosts = jpaJobPostRepository.findAll();

        return jobPosts.stream().map(jobPostPersistenceMapper::toDomainModel).toList();
    }

    //create job post
    @Override
    public JobPost createJobPost(JobPost jobPost){
        //set to entity
        JobPostEntity jobPostEntity = jobPostPersistenceMapper.toEntity(jobPost);
        
        //save in db and take save values
        JobPostEntity savedJobPostEntity = jpaJobPostRepository.save(jobPostEntity);
        //turn to domain model and return
        return jobPostPersistenceMapper.toDomainModel(savedJobPostEntity);
    }

    //update job post
    @Override
    public JobPost updateJobPost(
            Long postId,
            JobPost jobPost
    ){
        //check availability
        JobPostEntity jobPostEntity = jpaJobPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Post with id " + postId + " not found"));
        //update existing entity new domain model
        JobPostEntity updatedEntity = jobPostPersistenceMapper.updateEntityWithNewData(jobPost, jobPostEntity);
        //save in db
        JobPostEntity savedJobPostEntity =  jpaJobPostRepository.save(updatedEntity);
        //saved data turn into domain model
        return jobPostPersistenceMapper.toDomainModel(savedJobPostEntity);
    }

    //delete job post
    @Override
    public void deleteJobPost(Long postId){
        jpaJobPostRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Job Post with id " + postId + " not found"));
        jpaJobPostRepository.deleteById(postId);
    }
}
