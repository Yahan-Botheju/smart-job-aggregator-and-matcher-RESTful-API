package lk.job_finder_app.smart_job_aggregator.web.company.controllers;

import jakarta.validation.Valid;
import lk.job_finder_app.smart_job_aggregator.domain.models.Company;
import lk.job_finder_app.smart_job_aggregator.domain.models.enums.RoleName;
import lk.job_finder_app.smart_job_aggregator.globalResponseHandler.StandardResponse;
import lk.job_finder_app.smart_job_aggregator.usecase.company.CompanyUseCase;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.CreateCompanyCommand;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.CreateCompanyResult;
import lk.job_finder_app.smart_job_aggregator.usecase.company.records.GetAllCompaniesResult;
import lk.job_finder_app.smart_job_aggregator.web.company.DTOs.*;
import lk.job_finder_app.smart_job_aggregator.web.company.webMappers.CompanyWebMapper;
import lk.job_finder_app.smart_job_aggregator.web.security.Authorize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    //inject required dependencies
    private final CompanyUseCase companyUseCase;
    private final CompanyWebMapper companyWebMapper;

    //get all companies
    @GetMapping
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<StandardResponse<List<GetAllCompaniesResponseDTO>>> getAllCompanies(){

        List<GetAllCompaniesResult> companies = companyUseCase.getAllCompanies();
        List<GetAllCompaniesResponseDTO> responseDTOS = companies.stream()
                .map(companyWebMapper::toGetAllCompaniesResponseDTO).toList();

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Companies details fetched successfully",
                LocalDateTime.now(),
                responseDTOS
        ));

    }

    //create company
    @PostMapping
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<StandardResponse<CreateCompanyResponseDTO>> createCompany(
                              @Valid @RequestBody CreateCompanyRequestDTO createCompanyRequestDTO
    ){
        CreateCompanyCommand toCommand = companyWebMapper.toCreateCompanyCommand(createCompanyRequestDTO);
        CreateCompanyResult toResult = companyUseCase.createCompany(toCommand);
        CreateCompanyResponseDTO responseDTO = companyWebMapper.toCreateCompanyResponse(toResult);

        return ResponseEntity.created(URI.create("/api/v1/companies/" + responseDTO.getCompanyId()))
                .body(new StandardResponse<>(
                        201,
                        "Company registered successfully",
                        LocalDateTime.now(),
                        responseDTO
                ));
    }

    //update company
    @PutMapping("/{companyId}")
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<StandardResponse<CompanyResponseDTO>> updateCompany(
            @PathVariable Long companyId,
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO
    ){
        //create domain model
        Company toDomainModel = companyWebMapper.toDomainModel(companyRequestDTO);

        //set created domain model and id to usecase
        Company updatedCompany = companyUseCase.updateCompany(companyId, toDomainModel);

        return ResponseEntity.ok(new StandardResponse<>(
                200,
                "Details updated successfully",
                LocalDateTime.now(),
                companyWebMapper.toResponseDTO(updatedCompany)
        ));

    }

    //delete company
    @DeleteMapping("/{companyId}")
    @Authorize(RoleName.ADMIN)
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long companyId
    ){
        companyUseCase.deleteCompany(companyId);
        return  ResponseEntity.noContent().build();
    }

}
