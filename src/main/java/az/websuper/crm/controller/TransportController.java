package az.websuper.crm.controller;


import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.customer.CustomerUpdateDto;
import az.websuper.crm.dtos.transport.TransportCreateDto;
import az.websuper.crm.dtos.transport.TransportDto;
import az.websuper.crm.dtos.transport.TransportUpdateDto;
import az.websuper.crm.models.Transport;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;
import az.websuper.crm.services.CustomerService;
import az.websuper.crm.services.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/transport")
public class TransportController {


    @Autowired
    private TransportService transportService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity create(@RequestBody TransportCreateDto transportCreateDto, Principal principal)
    {
        transportService.createTransport(principal.getName(),transportCreateDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<PaginationPayload<TransportDto>> getAll(Principal principal, Integer pageNumber, Integer pageSize, String sortBy){
        PaginationPayload<TransportDto> response = transportService.getTransports(pageNumber,pageSize,sortBy,principal);
        return new ResponseEntity(response,HttpStatus.OK);
    }


    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody TransportUpdateDto transportUpdateDto, Principal principal)
    {
        ApiResponse response = transportService.updateTransport(principal.getName(), id , transportUpdateDto);
        return new ResponseEntity(response,HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, Principal principal)
    {
        ApiResponse response = transportService.removeTransport(principal.getName(),id);
        return new ResponseEntity(response,HttpStatus.OK);
    }


    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<CustomerUpdateDto> get(@PathVariable Long id, Principal principal)
    {
        TransportUpdateDto response = transportService.getUpdateTransport(principal.getName(),id);
        return new ResponseEntity(response,HttpStatus.OK);
    }

}
