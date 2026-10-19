package lk.job_finder_app.smart_job_aggregator.web.user.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    @NotBlank(message = "Username is required")
    private String userName;
    @Email
    @NotBlank(message = "Email cannot be empty")
    private String userEmail;

    private Set<String> skillsRequired;
}
