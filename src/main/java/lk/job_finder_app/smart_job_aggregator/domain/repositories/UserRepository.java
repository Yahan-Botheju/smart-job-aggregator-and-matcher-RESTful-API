package lk.job_finder_app.smart_job_aggregator.domain.repositories;

import lk.job_finder_app.smart_job_aggregator.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    //get user by ID
    Optional<User> userFindById(Long userId);

    //find user by email
    Optional<User> userFindByEmail(String email);

    //CUSTOM METHOD FOR RATE LIMIT
    Optional<User> findByIdWithSkills(Long userId);

    //get all users
    List<User> getAllUsers();

    //create user
    User createUser(User user);

    //update user
    User updateUser(Long userId, User user);

    //delete user
    void deleteUser(Long userId);
}
