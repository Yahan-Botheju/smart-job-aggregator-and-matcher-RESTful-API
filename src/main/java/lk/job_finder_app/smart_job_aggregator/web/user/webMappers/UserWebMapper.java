package lk.job_finder_app.smart_job_aggregator.web.user.webMappers;

import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.CreateUserCommand;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.CreateUserResult;
import lk.job_finder_app.smart_job_aggregator.usecase.user.records.GetAllUsersResults;
import lk.job_finder_app.smart_job_aggregator.web.user.DTOs.*;
import org.h2.command.ddl.CreateUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    //domain model to responseDTO
    UserResponseDTO toResponseDTO(User user);

    //requestDTO to domain model
    User toDomainModel(UserRequestDTO userRequestDTO);

    /* __GET_ALL_USERS__ */
    GetAllUsersResponseDTO getAllUsers(GetAllUsersResults getAllUsersResults);


    /* __CREATE_NEW_USER__ */

    //to command
    CreateUserCommand toCreateUserCommand(CreateUserRequestDTO createUserRequestDTO);

    //to response
    CreateUserResponseDTO toCreateUserResponseDTO(CreateUserResult createUserResult);
}
