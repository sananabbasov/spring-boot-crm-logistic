package az.websuper.crm.dtos.order;

import az.websuper.crm.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderChangeStatus {
    private Long id;
    private OrderStatus orderStatus;
}
