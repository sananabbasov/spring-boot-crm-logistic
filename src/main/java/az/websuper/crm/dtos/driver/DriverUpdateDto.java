package az.websuper.crm.dtos.driver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverUpdateDto {
    private String email;
    private String firstName;
    private String lastName;
}
