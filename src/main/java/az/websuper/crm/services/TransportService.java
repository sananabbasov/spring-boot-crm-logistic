package az.websuper.crm.services;

import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.dtos.transport.TransportCreateDto;
import az.websuper.crm.dtos.transport.TransportDto;
import az.websuper.crm.dtos.transport.TransportUpdateDto;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;

import java.security.Principal;

public interface TransportService {
    PaginationPayload<TransportDto> getTransports(Integer pageNumber, Integer pageSize, String sortBy, Principal principal);
    ApiResponse createTransport(String userEmail, TransportCreateDto transportCreateDto);
    ApiResponse updateTransport(String userEmail, Long id, TransportUpdateDto transportUpdateDto);
    TransportUpdateDto getUpdateTransport(String userEmail, Long id);
    ApiResponse removeTransport(String userEmail, Long id);
}
