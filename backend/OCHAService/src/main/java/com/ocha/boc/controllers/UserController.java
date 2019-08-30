package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.services.impl.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Update User", authorizations = {@Authorization(value = "Bearer")})
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

    @ApiOperation(value = "Get list Users", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/users")
    public ResponseEntity<UserResponse> getAllUsers() {
        UserResponse response = userService.getAllUser();
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Deactive user by userId", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/users/deactive/{userId}")
    public ResponseEntity<AbstractResponse> deActiveUser(@PathVariable String userId) {
        AbstractResponse response = userService.deActiveUser(userId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Active User By UserId", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/users/active/{userId}")
    public ResponseEntity<UserResponse> activeUser(@PathVariable String userId) {
        UserResponse response = userService.activeUser(userId);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Check User Exist By PhoneNumber")
    @GetMapping("/users/checking/{phone}")
    public ResponseEntity<AbstractResponse> findUserByPhoneNumber(@PathVariable(value = "phone") String phoneNumber) {
        log.info("START: find user by phone number: " + phoneNumber);
        AbstractResponse response = userService.findUserByPhoneNumber(phoneNumber);
        log.info("END: find user by phone number");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete User By PhoneNumber (Test only)", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/users/{phoneNumber}/delete")
    public ResponseEntity<AbstractResponse> deleteUserByPhoneNumber(@PathVariable(value = "phoneNumber") String phoneNumber) {
        log.info("START: delete user with phone number: " + phoneNumber);
        AbstractResponse response = userService.deleteUserByPhoneNumber(phoneNumber);
        log.info("END: delete user with phone number");
        return ResponseEntity.ok(response);
    }

}
