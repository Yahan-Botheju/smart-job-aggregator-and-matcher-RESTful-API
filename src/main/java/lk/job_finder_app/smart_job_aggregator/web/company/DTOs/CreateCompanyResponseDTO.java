package lk.job_finder_app.smart_job_aggregator.web.company.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyResponseDTO {
    private String companyId;
    private String companyName;
    private String companyIndustry;
    private String companyRating;
}
