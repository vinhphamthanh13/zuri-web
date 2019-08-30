package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.services.impl.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "Update User")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUserInformation(@RequestBody UserUpdateRequest request) {
        UserResponse response = userService.updateUserInformation(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find User By Id")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get list Users")
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> getAllUsers() {
        UserResponse response = userService.getAllUser();
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Deactive user by userId")
    @DeleteMapping("/users/deactive/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
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


    @ApiOperation(value = "Check User Exist By PhoneNumber")
    @GetMapping("/users/checking/{phone}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AbstractResponse> findUserByPhoneNumber(@PathVariable(value = "phone") String phoneNumber){
        log.info("START: find user by phone number: " + phoneNumber);
        AbstractResponse response = userService.findUserByPhoneNumber(phoneNumber);
        log.info("END: find user by phone number");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete User By PhoneNumber (Test only)")
    @DeleteMapping("/users/{phoneNumber}/delete")
    public ResponseEntity<AbstractResponse> deleteUserByPhoneNumber(@PathVariable(value = "phoneNumber") String phoneNumber){
        log.info("START: delete user with phone number: " + phoneNumber);
        AbstractResponse response = userService.deleteUserByPhoneNumber(phoneNumber);
        log.info("END: delete user with phone number");
        return ResponseEntity.ok(response);
    }

}
