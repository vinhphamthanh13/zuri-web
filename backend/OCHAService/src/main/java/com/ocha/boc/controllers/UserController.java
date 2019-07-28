package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.services.impl.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Create new user account")
    @PostMapping("/users")
    public ResponseEntity<UserResponse> newUser(@RequestBody UserLoginRequest request) {
        UserResponse response = userService.newUser(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update User")
    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUserInformation(@RequestBody UserUpdateRequest request) {
        UserResponse response = userService.updateUserInformation(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find User By Id")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get list Users")
    @GetMapping("/users")
    public ResponseEntity<UserResponse> getAllUsers() {
        UserResponse response = userService.getAllUser();
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Deactive user by userId")
    @DeleteMapping("/users/deactive/{userId}")
    public ResponseEntity<AbstractResponse> deActiveUser(@PathVariable String userId) {
        AbstractResponse response = userService.deActiveUser(userId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Active User By UserId")
    @PutMapping("/users/active/{userId}")
    public ResponseEntity<UserResponse> activeUser(@PathVariable String userId) {
        UserResponse response = userService.activeUser(userId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Send Verification Code")
    @PostMapping("/user/{countryCode}/{phoneNumber}")
    public ResponseEntity<AbstractResponse> sendVerificationCode(@PathVariable(value = "countryCode") String countryCode,@PathVariable(value = "phoneNumber") String phoneNumber){
        AbstractResponse response = userService.sendVerificationCode(countryCode,phoneNumber);
        return ResponseEntity.ok(response);
    }
}
