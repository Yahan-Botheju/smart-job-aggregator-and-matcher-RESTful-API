package lk.job_finder_app.smart_job_aggregator.usecase.user;

import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.domain.repositories.UserRepository;
import lk.job_finder_app.smart_job_aggregator.globalExceptionHandler.superClasses.ResourceNotFoundException;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.GetAllUsersResults;
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
    public User createUser(User user){
        return userRepository.createUser(user);
    }

    //update user
    @Override
    public User updateUser(
            Long userId,
            User user){
        //check user availability
        userRepository.userFindById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + " , " +  userId));

        return userRepository.updateUser(userId, user);
    }

    //delete user
    @Override
    public void deleteUser(Long userId){
        userRepository.userFindById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + " , " +  userId));

        userRepository.deleteUser(userId);
    }
}
