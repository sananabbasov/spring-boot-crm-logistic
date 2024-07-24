package az.websuper.crm.services;

import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.dtos.driver.DriverCreateDto;
import az.websuper.crm.dtos.driver.DriverDto;
import az.websuper.crm.dtos.driver.DriverUpdateDto;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;

import java.security.Principal;
import java.util.List;

public interface DriverService {

    PaginationPayload<DriverDto> getDrivers(Integer pageNumber, Integer pageSize, String sortBy, Principal principal);
    ApiResponse createDriver(String userEmail, DriverCreateDto driverCreateDto);
    ApiResponse updateDriver(String userEmail, Long id, DriverUpdateDto driverUpdateDto);
    DriverUpdateDto getUpdateDriver(String userEmail, Long id);
    ApiResponse removeDriver(String userEmail, Long id);
    List<DriverDto> getDrivers(String adminEmail);
}
