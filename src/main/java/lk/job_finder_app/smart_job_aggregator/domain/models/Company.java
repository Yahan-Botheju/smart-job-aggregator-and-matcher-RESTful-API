package lk.job_finder_app.smart_job_aggregator.domain.models;

import java.math.BigDecimal;


public class Company {
    private Long companyId;
    private String companyName;
    private String companyIndustry;
    private BigDecimal companyRating;

    public Company(Long companyId, String companyName, String companyIndustry, BigDecimal companyRating) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyIndustry = companyIndustry;
        this.companyRating = companyRating;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public BigDecimal getCompanyRating() {
        return companyRating;
    }

    /* __CREATE_STATIC_METHOD__ */
    public static Company createCompany(String companyName, String companyIndustry, BigDecimal companyRating) {
        return new Company(null, companyName, companyIndustry, companyRating);
    }

    /* __UPDATE_COMPANY__ */

    public void updateCompany(String companyName, String companyIndustry, BigDecimal companyRating) {
        this.companyName = companyName;
        this.companyIndustry = companyIndustry;
        this.companyRating = companyRating;
    }
}
