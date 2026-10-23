package lk.job_finder_app.smart_job_aggregator.infrastructure.persistence.company.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.math.BigDecimal;


@Entity
@Table(name = "companies")
@SoftDelete(columnName = "is_deleted")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    @NotBlank(message = "company name cannot be empty")
    private String companyName;
    @NotBlank(message = "Company industry cannot be empty")
    private String companyIndustry;
    @NotNull(message = "Rating cannot be empty")
    private BigDecimal companyRating;
}
