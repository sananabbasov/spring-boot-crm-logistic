package az.websuper.crm.controller;

import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.dtos.driver.DriverCreateDto;
import az.websuper.crm.dtos.driver.DriverDto;
import az.websuper.crm.dtos.driver.DriverUpdateDto;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;
import az.websuper.crm.services.DriverService;
import az.websuper.crm.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/driver")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/drivers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DriverDto>> getDrivers(Principal principal){
        String adminEmail = principal.getName();
        List<DriverDto> drivers = driverService.getDrivers(adminEmail);
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity create(@RequestBody DriverCreateDto driverCreateDto, Principal principal)
    {
        driverService.createDriver(principal.getName(),driverCreateDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<PaginationPayload<DriverDto>> getAll(Principal principal, Integer pageNumber, Integer pageSize, String sortBy){
        PaginationPayload<DriverDto> response = driverService.getDrivers(pageNumber,pageSize,sortBy,principal);
        return new ResponseEntity(response,HttpStatus.OK);
    }


    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody DriverUpdateDto driverUpdateDto, Principal principal)
    {
        ApiResponse response = driverService.updateDriver(principal.getName(), id , driverUpdateDto);
        return new ResponseEntity(response,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, Principal principal)
    {
        ApiResponse response = driverService.removeDriver(principal.getName(),id);
        return new ResponseEntity(response,HttpStatus.OK);
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<DriverUpdateDto> get(@PathVariable Long id, Principal principal)
    {
        DriverUpdateDto response = driverService.getUpdateDriver(principal.getName(),id);
        return new ResponseEntity(response,HttpStatus.OK);
    }
}
