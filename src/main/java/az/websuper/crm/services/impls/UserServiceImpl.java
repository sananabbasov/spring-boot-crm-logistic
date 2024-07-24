package az.websuper.crm.services.impls;

import az.websuper.crm.dtos.auth.RegisterDto;
import az.websuper.crm.dtos.auth.RegisterEmployeeDto;
import az.websuper.crm.dtos.driver.DriverDto;
import az.websuper.crm.models.Company;
import az.websuper.crm.models.Role;
import az.websuper.crm.models.User;
import az.websuper.crm.repositories.CompanyRepository;
import az.websuper.crm.repositories.UserRepository;
import az.websuper.crm.services.CompanyService;
import az.websuper.crm.services.RoleService;
import az.websuper.crm.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleService roleService;

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

    @Override
    public void registerEmployee(String adminEmail,RegisterEmployeeDto registerEmployeeDto) {
        User findUserCompany = userRepository.findByEmail(adminEmail);
        User user = modelMapper.map(registerEmployeeDto, User.class);
        user.setCompany(findUserCompany.getCompany());
        String password = passwordEncoder.encode(registerEmployeeDto.getPassword());
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public User findByLoggedUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<DriverDto> getDrivers(String adminEmail) {
        User findUserCompany = userRepository.findByEmail(adminEmail);
        List<Role> roles = new ArrayList<>();
        Role role= roleService.getRoleByName("DRIVER");
        roles.add(role);
        List<User> users = userRepository.findAll().stream().filter(c-> c.getRoles().contains(role) && c.getCompany().getId().equals(findUserCompany.getCompany().getId())).collect(Collectors.toList());

        List<DriverDto> drivers = users.stream().map(x-> modelMapper.map(x, DriverDto.class)).collect(Collectors.toList());
        return drivers;
    }
}
