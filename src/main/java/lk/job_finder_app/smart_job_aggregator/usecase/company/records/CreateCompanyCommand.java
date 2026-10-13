package lk.job_finder_app.smart_job_aggregator.usecase.company.records;

import java.math.BigDecimal;

public record CreateCompanyCommand(
        String companyName,
        String companyIndustry,
        BigDecimal companyRating
) {
}
