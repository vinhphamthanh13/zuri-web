package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.OTPRequest;
import com.ocha.boc.request.SendOTPRequest;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.response.JwtAuthenticationResponse;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.services.impl.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Slf4j
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
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestParam OTPRequest request) {
        log.info("START: login API");
        JwtAuthenticationResponse response = authService.login(request);
        log.info("END: login API");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Send OTP")
    @PostMapping("/users/sendOTP")
    public ResponseEntity<AbstractResponse> sendOTP(@RequestBody SendOTPRequest request) {
        log.info("START: Sending OTP to phone number: " + request.getPhoneNumber());
        AbstractResponse response = authService.sendOTP(request);
        log.info("END: Sending OTP to phone number");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Verify OTP")
    @PostMapping("/users/verifyOTP")
    public ResponseEntity<JwtAuthenticationResponse> verifyOTP(@RequestBody OTPRequest request) {
        log.info("START: Verify OTP for phone number: " + request.getPhoneNumber());
        JwtAuthenticationResponse response = authService.verifyOTP(request);
        log.info("END: Verify OTP");
        return ResponseEntity.ok(response);
    }

}
