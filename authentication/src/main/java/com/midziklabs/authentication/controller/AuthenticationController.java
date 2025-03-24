package com.midziklabs.authentication.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.midziklabs.authentication.AuthenticationApplication;
import com.midziklabs.authentication.configuration.ApplicationConfiguration;
import com.midziklabs.authentication.configuration.AuthConfig;
import com.midziklabs.authentication.dto.LoginResponse;
import com.midziklabs.authentication.dto.LoginUserDto;
import com.midziklabs.authentication.dto.RegisterUserDto;
import com.midziklabs.authentication.model.RoleModel;
import com.midziklabs.authentication.model.UserModel;
import com.midziklabs.authentication.repository.RoleRepository;
import com.midziklabs.authentication.service.AuthenticationService;
import com.midziklabs.authentication.service.JwtService;
import com.midziklabs.authentication.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationApplication authenticationApplication;

    private final AuthConfig authConfig;

    private final ApplicationConfiguration applicationConfiguration;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @GetMapping("/welcome")
    public String getWelcomePage() {
        return "Hello Weorld ffrom AuthenticationController";
    }
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('User')")
    public String getUser() {
        return "Hello User";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('Administrator')")
    public String getAdmin() {
        return "Hello Admin";
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody RegisterUserDto registerUserDto){
        UserModel registeredUser = authenticationService.register(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        UserModel authenticatedUSer = authenticationService.login(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUSer);
        // Cookie cookie = new Cookie("token", jwtToken);
        // cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        // cookie.setMaxAge(1800);
        // cookie.setPath("/");
        // response.addCookie(cookie);
        // log.info("LOGIN REPOSNSE");
        // log.info(response.toString());
        LoginResponse response = new LoginResponse();
        response.setToken(jwtToken);
        response.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/roles")
    public List<RoleModel> getRoles(@RequestParam String param) {
        return roleRepository.findAll();
    }
    @GetMapping("/auth-user")
    public ResponseEntity<?> getAuthenticatedUser(){
        Optional<UserModel> user = authenticationService.getAuthenticatedUser();
        // Optional<UserModel> user = userService.getUserByEmail(email);
        log.info("User retrieved: "+user);
        if(user.isPresent()){
            log.info("User is present");
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(
                "User not found"
            );
        }
    }
    
    
    
}
