package az.websuper.crm.services;

import az.websuper.crm.dtos.auth.RegisterDto;

public interface UserService {
    void register(RegisterDto registerDto);
}
