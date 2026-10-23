package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.role.jpa;

import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.role.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleEntity, Long> {

    //create custom query for find role name
    Optional<RoleEntity> findByRoleName(RoleName roleName);
}
