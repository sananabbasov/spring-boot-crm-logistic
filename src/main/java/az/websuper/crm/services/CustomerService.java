package az.websuper.crm.services;

import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;

import java.security.Principal;

public interface CustomerService {
    PaginationPayload<CustomerDto> getCustomers(Integer pageNumber, Integer pageSize, String sortBy, Principal principal);
    ApiResponse createCustomer(String userEmail, CustomerCreateDto customerCreateDto);
    ApiResponse updateCustomer(Long id, CustomerUpdateDto customerUpdateDto);


}
