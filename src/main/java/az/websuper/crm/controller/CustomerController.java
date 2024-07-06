package az.websuper.crm.controller;

import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.payloads.PaginationPayload;
import az.websuper.crm.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity create(@RequestBody CustomerCreateDto customerCreateDto, Principal principal)
    {
        customerService.createCustomer(principal.getName(),customerCreateDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<PaginationPayload<CustomerDto>> getAll(Principal principal, Integer pageNumber, Integer pageSize, String sortBy){
        PaginationPayload<CustomerDto> response = customerService.getCustomers(pageNumber,pageSize,sortBy,principal);
        return new ResponseEntity(response,HttpStatus.OK);
    }

}
