package lk.job_finder_app.smart_job_aggregator.web.user.webMappers;

import lk.job_finder_app.smart_job_aggregator.usecase.user.records.*;
import lk.job_finder_app.smart_job_aggregator.web.user.DTOs.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    /* __GET_ALL_USERS__ */
    GetAllUsersResponseDTO getAllUsers(GetAllUsersResults getAllUsersResults);


    /* __CREATE_NEW_USER__ */

    //to command
    CreateUserCommand toCreateUserCommand(CreateUserRequestDTO createUserRequestDTO);

    //to response
    CreateUserResponseDTO toCreateUserResponseDTO(CreateUserResult createUserResult);

    /* __UPDATE_USER__ */

    //to command
    UpdateUserCommand toUpdateUserCommand(Long userId, UpdateUserRequestDTO updateUserRequestDTO);

    //to response
    UpdateUserResponseDTO toUpdateUserResponseDTO(UpdateUserResult updateUserResult);

    /* __DELETE_USER__ */

    //to command
    DeleteUserCommand toDeleteUserCommand(Long userId);
}
