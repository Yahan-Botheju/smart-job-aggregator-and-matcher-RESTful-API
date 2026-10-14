package lk.job_finder_app.smart_job_aggregator.web.company.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompanyRequestDTO {
    @NotBlank(message = "Company Name cannot be empty")
    private String companyName;
    @NotBlank(message = "Company Industry cannot be empty")
    private String companyIndustry;
    @NotNull(message = "Company rating cannot be empty" )
    private BigDecimal companyRating;
}
