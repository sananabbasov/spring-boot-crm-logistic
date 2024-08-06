package az.websuper.crm.controller;


import az.websuper.crm.dtos.customer.CustomerCreateDto;
import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.order.OrderChangeStatus;
import az.websuper.crm.dtos.order.OrderCreateDto;
import az.websuper.crm.dtos.order.OrderDto;
import az.websuper.crm.enums.OrderStatus;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;
import az.websuper.crm.services.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("api/order")
public class OrderController {

    @Value("${value.smtp.number}")
    private String numberValue;
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity create(@RequestBody OrderCreateDto orderCreateDto, Principal principal)
    {

        orderService.createOrder(principal.getName(),orderCreateDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<PaginationPayload<CustomerDto>> getAll(Principal principal, Integer pageNumber, Integer pageSize, String sortBy, Optional<OrderStatus> orderStatus){
        String res = numberValue;
        PaginationPayload<OrderDto> response = orderService.getOrders(pageNumber,pageSize,sortBy,principal,orderStatus.get());
        return new ResponseEntity(response,HttpStatus.OK);
    }

    @PutMapping("/status")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ApiResponse> changeStatus(@RequestBody OrderChangeStatus orderChangeStatus, Principal principal)
    {
        ApiResponse response = orderService.changeStatus(principal.getName(),orderChangeStatus);
        if (response.isSuccess()){
            return new ResponseEntity(response,HttpStatus.OK);
        }
        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

}
