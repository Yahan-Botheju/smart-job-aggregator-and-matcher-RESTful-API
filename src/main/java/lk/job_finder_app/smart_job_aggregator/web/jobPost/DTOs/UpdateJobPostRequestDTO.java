package lk.job_finder_app.smart_job_aggregator.web.jobPost.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateJobPostRequestDTO {
    @NotBlank(message = "post title cannot be empty")
    private String postTitle;
    @NotBlank(message = "post description cannot be empty")
    private String postDescription;
    @NotNull(message = "post salary cannot be empty")
    private BigDecimal postSalary;
    @NotEmpty(message = "at least one skill is required")
    private Set<String> skillsRequired;
}
