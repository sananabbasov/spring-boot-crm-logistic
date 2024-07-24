package az.websuper.crm.services.impls;

import az.websuper.crm.exceptions.ResourceNotFoundException;
import az.websuper.crm.models.Role;
import az.websuper.crm.repositories.RoleRepository;
import az.websuper.crm.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String roleName) {
        Role role =roleRepository.findByName(roleName).orElseThrow();
        return role;
    }
}
