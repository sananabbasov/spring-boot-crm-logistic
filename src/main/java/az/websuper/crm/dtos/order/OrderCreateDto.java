package az.websuper.crm.dtos.order;

import az.websuper.crm.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateDto {
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
    private Long customerId;
    private Long driverId;
}
