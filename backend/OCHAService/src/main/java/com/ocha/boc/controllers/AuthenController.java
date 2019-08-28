package com.ocha.boc.controllers;

import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.response.JwtAuthenticationResponse;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.security.jwt.JwtTokenProvider;
import com.ocha.boc.services.impl.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenController {



    @Autowired
    private AuthService authService;



    @Value("${app.debug}")
    private boolean debug;

    @ApiOperation(value = "register new user account")
    @PostMapping("/users")
    public ResponseEntity<UserResponse> register(@RequestBody UserLoginRequest request) {
        log.info("START: register new user account");
        UserResponse response = authService.register(request);
        log.info("END: register new user account");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "login")
    @PostMapping("/users/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestParam String phoneNumber) {
        log.info("START: login API");
        JwtAuthenticationResponse response = authService.login(phoneNumber);
        log.info("END: login API");
        return ResponseEntity.ok(response);
    }


}
