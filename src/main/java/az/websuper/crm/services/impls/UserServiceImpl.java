package az.websuper.crm.services.impls;

import az.websuper.crm.dtos.auth.RegisterDto;
import az.websuper.crm.models.Company;
import az.websuper.crm.models.User;
import az.websuper.crm.repositories.CompanyRepository;
import az.websuper.crm.repositories.UserRepository;
import az.websuper.crm.services.CompanyService;
import az.websuper.crm.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDto registerDto) {
        Company company = companyService.createCompany(registerDto.getCompany());
        User user = modelMapper.map(registerDto, User.class);
        user.setCompany(company);
        String password = passwordEncoder.encode(registerDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }
}
