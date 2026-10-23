package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.mapper;

import lk.job_finder_app.smart_job_aggregator.domain.models.User;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserPersistenceMapper {

    //domain model to entity
    UserEntity toEntity(User user);

    //entity to domain model
    User toDomainModel(UserEntity userEntity);

    //update existing entity by new domain model
    UserEntity updateEntity(User user, @MappingTarget UserEntity userEntity );

}
