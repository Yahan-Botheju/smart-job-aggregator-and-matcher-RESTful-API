package lk.job_finder_app.smart_job_aggregator.web.company.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCompanyRequestDTO {
    @NotNull(message = "Company ID cannot be empty")
    private Long companyId;
}
