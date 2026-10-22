package lk.job_finder_app.smart_job_aggregator.web.company.webMappers;

import lk.job_finder_app.smart_job_aggregator.usecase.company.records.*;
import lk.job_finder_app.smart_job_aggregator.web.company.DTOs.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyWebMapper {

    /* __GET_ALL_COMPANIES__ */

    //to response
    GetAllCompaniesResponseDTO toGetAllCompaniesResponseDTO(GetAllCompaniesResult getAllCompaniesResult);


    /* __CREATE_COMPANY__ */

    //to command
    CreateCompanyCommand toCreateCompanyCommand(CreateCompanyRequestDTO createCompanyRequestDTO);

    //to response
    CreateCompanyResponseDTO toCreateCompanyResponse(CreateCompanyResult  createCompanyResult);


    /* __UPDATE_COMPANY__ */

    //to command
    UpdateCompanyCommand toUpdateCompanyCommand(Long companyId, UpdateCompanyRequestDTO updateCompanyRequestDTO);

    //to response
    UpdateCompanyResponseDTO toUpdateCompanyResponse(UpdateCompanyResult updateCompanyResult);


    /* __DELETE_COMPANY__ */

    //to command
    DeleteCompanyCommand toDeleteCompanyCommand(Long companyId);


}
