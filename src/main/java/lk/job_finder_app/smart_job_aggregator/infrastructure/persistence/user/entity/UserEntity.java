package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.role.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.util.Set;


@Entity
@Table(name = "users")
@SoftDelete(columnName = "is_deleted")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "user name is required")
    private String userName;
    @Email
    @NotBlank(message = "Email is required")
    private String userEmail;

    @ElementCollection
    @CollectionTable(name = "job_post_skills", joinColumns = @JoinColumn(name = "postId"))
    private Set<String> skillsRequired;

    @ManyToOne
    @JoinColumn(name = "roleId",  nullable = false)
    private RoleEntity role;
}
