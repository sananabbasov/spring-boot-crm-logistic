package az.websuper.crm.dtos.auth;

import az.websuper.crm.dtos.company.CompanyCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private CompanyCreateDto company;
}