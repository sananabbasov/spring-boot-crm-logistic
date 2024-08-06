package az.websuper.crm.messages;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatusChangeMessages {
    private Long orderId;
    private String customerFullName;
    private String phoneNumber;
    private String email;
}
