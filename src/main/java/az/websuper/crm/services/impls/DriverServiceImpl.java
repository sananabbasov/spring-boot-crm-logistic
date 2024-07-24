package az.websuper.crm.services.impls;

import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.dtos.driver.DriverCreateDto;
import az.websuper.crm.dtos.driver.DriverDto;
import az.websuper.crm.dtos.driver.DriverUpdateDto;
import az.websuper.crm.models.Customer;
import az.websuper.crm.models.Role;
import az.websuper.crm.models.User;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;
import az.websuper.crm.repositories.UserRepository;
import az.websuper.crm.services.DriverService;
import az.websuper.crm.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public DriverServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @Override
    public PaginationPayload<DriverDto> getDrivers(Integer pageNumber, Integer pageSize, String sortBy, Principal principal) {
        Sort sort = null;
        if (!sortBy.equalsIgnoreCase("asc"))
        {
            sort = Sort.by(sortBy).ascending();
        }else
        {
            sort = Sort.by(sortBy).descending();
        }
        List<Role> roles = new ArrayList<>();
        Role role= roleService.getRoleByName("DRIVER");
        roles.add(role);
        User findUserCompany = userRepository.findByEmail(principal.getName());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> users = userRepository.findByCompanyId(pageable,findUserCompany.getCompany().getId());
        List<User> result = users.getContent();
        List<DriverDto> driverDtos = result.stream().filter(c-> c.getRoles().contains(role)).map(c -> modelMapper.map(c, DriverDto.class)).collect(Collectors.toList());

        PaginationPayload<DriverDto> postResponse = new PaginationPayload<>();
        postResponse.setData(driverDtos);
        postResponse.setPageNumber(pageable.getPageNumber());
        postResponse.setPageSize(pageable.getPageNumber());
        postResponse.setTotalElements(pageable.getPageSize());
        postResponse.setTotalPages(pageable.getPageSize());
        postResponse.setLastPage(pageable.isPaged());

        return postResponse;
    }

    @Override
    public ApiResponse createDriver(String userEmail, DriverCreateDto driverCreateDto) {
        User findUserCompany = userRepository.findByEmail(userEmail);
        User user = modelMapper.map(driverCreateDto, User.class);
        user.setCompany(findUserCompany.getCompany());
        Random rnd = new Random();
        String res  = String.valueOf(rnd.nextInt(2000,8000));
        String password = passwordEncoder.encode(res);
        user.setPassword(password);
        userRepository.save(user);
        return null;
    }

    @Override
    public ApiResponse updateDriver(String userEmail, Long id, DriverUpdateDto driverUpdateDto) {
        return null;
    }

    @Override
    public DriverUpdateDto getUpdateDriver(String userEmail, Long id) {
        try {
            User findDriver = userRepository.findById(id).orElseThrow();
            User findUserCompany = userRepository.findByEmail(userEmail);
            if (!findUserCompany.getCompany().getName().equals(findDriver.getCompany().getName())){
                return null;
            }
            DriverUpdateDto result = modelMapper.map(findDriver, DriverUpdateDto.class);
            return result;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public ApiResponse removeDriver(String userEmail, Long id) {
        return null;
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
