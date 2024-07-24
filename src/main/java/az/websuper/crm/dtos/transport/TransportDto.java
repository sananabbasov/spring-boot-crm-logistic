package az.websuper.crm.dtos.transport;

import az.websuper.crm.enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportDto {
    private Long id;
    private String registrationPlate;
    private TransportType transportType;
}