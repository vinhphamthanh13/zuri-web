package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.entity.User;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.services.impl.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserResponse response = new UserResponse();
        User user = userService.getUserById(id);
        if (user != null) {
            UserDTO userDTO = new UserDTO(user);
            response.setObject(userDTO);
            response.setSuccess(Boolean.TRUE);
            response.setTotalResultCount(1L);
        } else {
            response.setTotalResultCount(0L);
            response.setSuccess(Boolean.FALSE);
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get list Users")
    @GetMapping("/users")
    public ResponseEntity<UserResponse> getAllUsers() {
        UserResponse response = new UserResponse();
        List<UserDTO> userDTOList = userService.getAllUser();
        response.setObjects(userDTOList);
        response.setSuccess(Boolean.TRUE);
        response.setTotalResultCount(userDTOList.size() * 1L);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete User By Id")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<AbstractResponse> deleteUserById(@PathVariable String userId) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.TRUE);
        try {
            userService.deleteUserById(userId);
            response.setMessage("OK");
        } catch (Exception e) {
            response.setSuccess(Boolean.FALSE);
            response.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Deactive user by userId")
    @GetMapping("/users/deactive/{userId}")
    public ResponseEntity<AbstractResponse> deActiveUser(@PathVariable String userId) {
        AbstractResponse response = new AbstractResponse();
        boolean isDeActive = userService.deActiveUser(userId);
        if (isDeActive) {
            response.setSuccess(Boolean.TRUE);
            response.setMessage("OK");
        } else {
            response.setSuccess(Boolean.FALSE);
            response.setMessage("FAILED");
        }
        return ResponseEntity.ok(response);
    }
}
