package lk.job_finder_app.smart_job_aggregator.web.user.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllUsersResponseDTO {
    private String userId;
    private String userName;
    private String userEmail;
    private String role;
    private Set<String> skillsRequired;
}
