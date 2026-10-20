package lk.job_finder_app.smart_job_aggregator.usecase.user;

import lk.job_finder_app.smart_job_aggregator.domain.models.Role;
import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.globalExceptionHandler.superClasses.IllegalStateException;
import lk.job_finder_app.smart_job_aggregator.globalExceptionHandler.superClasses.ResourceNotFoundException;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase{

    //inject user repo
    private final UserRepository userRepository;

    //get all users
    @Override
    public List<GetAllUsersResults> getAllUsers(){
        return userRepository.getAllUsers().stream()
                .map(user -> new GetAllUsersResults(
                        user.getUserId().toString(),
                        user.getUserName(),
                        user.getUserEmail(),
                        user.getRole().toString(),
                        user.getSkillsRequired()
                )).toList();
    }

    //create user
    @Override
    public CreateUserResult createUser(CreateUserCommand createUserCommand) {
        //check user exists
        if (userRepository.userFindByEmail(createUserCommand.userEmail()).isPresent()){
            throw new IllegalStateException("User already exists");
        }

        //create user through domain
        User createNewUser = User.createNewUser(
                createUserCommand.userName(),
                createUserCommand.userEmail(),
                RoleName.USER,
                createUserCommand.skillsRequired()
        );

        User savedUser = userRepository.createUser(createNewUser);

        return new CreateUserResult(
                savedUser.getUserId().toString(),
                savedUser.getUserName(),
                savedUser.getUserEmail(),
                savedUser.getRole().toString(),
                savedUser.getSkillsRequired()
        );
    }

    //update user
    @Override
    public UpdateUserResult updateUser(UpdateUserCommand updateUserCommand) {
        //check user availability
        User user = userRepository.userFindById(updateUserCommand.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + " , " +  updateUserCommand.userId()));

        //update user through domain model
        user.updateUser(
                updateUserCommand.userName(),
                updateUserCommand.userEmail(),
                user.getRole(),
                updateUserCommand.skillsRequired()
        );

        User savedUser = userRepository.updateUser(user);

        return new  UpdateUserResult(
                savedUser.getUserId().toString(),
                savedUser.getUserName(),
                savedUser.getUserEmail(),
                savedUser.getRole().toString(),
                savedUser.getSkillsRequired()
        );
    }

    //delete user
    @Override
    public void deleteUser(Long userId){
        userRepository.userFindById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + " , " +  userId));

        userRepository.deleteUser(userId);
    }
}
