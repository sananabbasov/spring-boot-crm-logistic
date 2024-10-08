package az.websuper.crm.controller;


import az.websuper.crm.dtos.auth.*;
import az.websuper.crm.dtos.driver.DriverDto;
import az.websuper.crm.models.RefreshToken;
import az.websuper.crm.security.JwtService;
import az.websuper.crm.services.RefreshTokenService;
import az.websuper.crm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/auth")
public class AuthController {


    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto registerDto){
        userService.register(registerDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/register-employee")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity registerEmployee(@RequestBody RegisterEmployeeDto registerDto, Principal principal){
        String adminEmail = principal.getName();
        userService.registerEmployee(adminEmail,registerDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public JwtResponseDto AuthenticateAndGetToken(@RequestBody LoginDto authRequestDTO){
        refreshTokenService.removeToken(authRequestDTO.getEmail());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getEmail());
            return JwtResponseDto.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getEmail()))
                    .token(refreshToken.getToken())
                    .build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }



}
