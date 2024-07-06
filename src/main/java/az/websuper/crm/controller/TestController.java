package az.websuper.crm.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("/test")
    @PreAuthorize("hasAnyAuthority('TEST','TESTS')")
    public String test(Principal principal){
        String user = principal.getName();

        return "Salam";
    }
}
