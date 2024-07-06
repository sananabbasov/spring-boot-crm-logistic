package az.websuper.crm.services.impls;

import az.websuper.crm.dtos.company.CompanyCreateDto;
import az.websuper.crm.models.Company;
import az.websuper.crm.repositories.CompanyRepository;
import az.websuper.crm.services.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Company createCompany(CompanyCreateDto companyCreateDto) {
        Company company = modelMapper.map(companyCreateDto, Company.class);
        company.setExpirationDate(new Date());
        companyRepository.save(company);
        return company;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow();
    }
}
