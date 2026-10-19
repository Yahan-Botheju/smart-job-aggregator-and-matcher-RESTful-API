package lk.job_finder_app.smart_job_aggregator.usecase.user;

import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.CreateUserCommand;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.CreateUserResult;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.GetAllUsersResults;

import java.util.List;

public interface UserUseCase {

    //get all users
    List<GetAllUsersResults> getAllUsers();

    //create user
    CreateUserResult createUser(CreateUserCommand createUserCommand);

    //update user
    User updateUser(Long userId, User user);

    //delete user
    void deleteUser(Long userId);
}
