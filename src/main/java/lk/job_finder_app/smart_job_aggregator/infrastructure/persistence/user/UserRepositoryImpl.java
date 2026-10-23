package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user;

import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.role.entity.RoleEntity;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.role.jpa.JpaRoleRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.entity.UserEntity;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.jpa.JpaUserRepository;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.mapper.UserPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    //inject user jpa repo
    private final JpaUserRepository jpaUserRepository;

    //inject user persistence mapper
    private final UserPersistenceMapper userPersistenceMapper;

    //inject role jpa repo
    private final JpaRoleRepository jpaRoleRepository;


    /*  ----- HELPER METHODS -----  */


    //get user by ID (CUSTOM METHOD FOR VALIDATION)
    @Override
    public Optional<User> userFindById(Long userId){
        return jpaUserRepository.findById(userId).map(userPersistenceMapper::toDomainModel);
    }

    @Override
    public Optional<User> userFindByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(userPersistenceMapper::toDomainModel);
    }

    //create custom method for rate limit to prevent lazy loading
    @Override
    public Optional<User> findByIdWithSkills(Long userId){
        return jpaUserRepository.findByIdWithSkills(userId).map(userPersistenceMapper::toDomainModel);
    }



    /*  ----- PUBLIC METHODS -----  */

    //get all users
    @Override
    public List<User> getAllUsers(){
        List<UserEntity>  userEntities = jpaUserRepository.findAll();
        return userEntities.stream().map(userPersistenceMapper::toDomainModel).toList();
    }

    //create user
    @Override
    public User createUser(User user){
        //domain model to entity
        UserEntity userEntity = userPersistenceMapper.toEntity(user);

        //call custom query
        RoleEntity defaultRole = jpaRoleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));

        //set default role
        userEntity.setRole(defaultRole);

        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);
        //turn entity do domain model return for response
        return userPersistenceMapper.toDomainModel(savedUserEntity);
    }

    //update user
    @Override
    public User updateUser(User user){
        //update user and save in db
        UserEntity userEntity = userPersistenceMapper.toEntity(user);
        //turn to entity and save in db
        UserEntity savedUserEntity = jpaUserRepository.save(userEntity);

        //return as response
        return userPersistenceMapper.toDomainModel(savedUserEntity);
    }

    //delete user
    @Override
    public void deleteUser(Long userId){
       jpaUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + " , " +  userId));

        jpaUserRepository.deleteById(userId);
    }
}
