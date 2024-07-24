package az.websuper.crm.services;

import az.websuper.crm.dtos.auth.RegisterDto;
import az.websuper.crm.dtos.auth.RegisterEmployeeDto;
import az.websuper.crm.dtos.driver.DriverDto;
import az.websuper.crm.models.User;

import java.util.List;

public interface UserService {
    void register(RegisterDto registerDto);
    void registerEmployee(String adminEmail, RegisterEmployeeDto registerEmployeeDto);

    User findByLoggedUser(String email);


    List<DriverDto> getDrivers(String adminEmail);
}
