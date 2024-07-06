package az.websuper.crm.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterEmployeeDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
