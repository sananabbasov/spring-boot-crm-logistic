package az.websuper.crm.models;

import az.websuper.crm.enums.TransportType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name = "transports")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationPlate;
    private TransportType transportType;

    @ManyToOne
    private Company company;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_transport", joinColumns = @JoinColumn(name = "transports", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "users", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();
}
