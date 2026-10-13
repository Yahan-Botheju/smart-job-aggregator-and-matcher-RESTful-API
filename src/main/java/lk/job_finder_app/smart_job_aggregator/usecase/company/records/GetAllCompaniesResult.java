package lk.job_finder_app.smart_job_aggregator.usecase.company.records;


public record GetAllCompaniesResult(
        String companyId,
        String companyName,
        String companyIndustry,
        String companyRating
) {
}
