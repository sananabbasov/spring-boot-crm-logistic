package az.websuper.crm.dtos.order;

import az.websuper.crm.dtos.customer.CustomerDto;
import az.websuper.crm.dtos.driver.DriverDto;
import az.websuper.crm.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long number;
    private OrderStatus orderStatus;
    private Date pickupDate;
    private Date deliveryDate;
    private String pickupAddress;
    private String deliveryAddress;
    private float weight;
    private float price;
    private float tax;
    private CustomerDto customer;
    private DriverDto driver;
}
