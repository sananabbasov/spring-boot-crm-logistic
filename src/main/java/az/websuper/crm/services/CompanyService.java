package az.websuper.crm.services;

import az.websuper.crm.dtos.company.CompanyCreateDto;
import az.websuper.crm.models.Company;

public interface CompanyService {
    Company createCompany(CompanyCreateDto companyCreateDto);
}
