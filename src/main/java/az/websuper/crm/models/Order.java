package az.websuper.crm.models;


import az.websuper.crm.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;
    private Date pickupDate;
    private Date deliveryDate;
    private String pickupAddress;
    private String deliveryAddress;
    private Long weight;
    private Long price;
    private Long tax;
    private Boolean deleted;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private User employee;
    @ManyToOne
    private User driver;
    @ManyToOne
    private Company company;
}
