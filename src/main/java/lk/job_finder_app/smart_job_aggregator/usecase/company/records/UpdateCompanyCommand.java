package lk.job_finder_app.smart_job_aggregator.usecase.company.records;

import java.math.BigDecimal;

public record UpdateCompanyCommand(
        Long companyId,
        String companyName,
        String companyIndustry,
        BigDecimal companyRating
) {
}
