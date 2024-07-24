package az.websuper.crm.services;

import az.websuper.crm.dtos.order.OrderChangeStatus;
import az.websuper.crm.dtos.order.OrderCreateDto;
import az.websuper.crm.dtos.order.OrderDto;
import az.websuper.crm.dtos.order.OrderUpdateDto;
import az.websuper.crm.enums.OrderStatus;
import az.websuper.crm.payloads.ApiResponse;
import az.websuper.crm.payloads.PaginationPayload;

import java.security.Principal;

public interface OrderService {
    PaginationPayload<OrderDto> getOrders(Integer pageNumber, Integer pageSize, String sortBy, Principal principal, OrderStatus orderStatus);
    ApiResponse createOrder(String userEmail, OrderCreateDto OrderCreateDto);
    ApiResponse updateOrder(String userEmail, Long id, OrderUpdateDto orderUpdateDto);
    OrderUpdateDto getUpdateOrder(String userEmail, Long id);
    ApiResponse removeOrder(String userEmail, Long id);
    ApiResponse changeStatus(String userEmail, OrderChangeStatus orderChangeStatus);
}
