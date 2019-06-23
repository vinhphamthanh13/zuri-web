package com.ocha.boc.controllers;

import com.ocha.boc.entity.User;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserLoginResponse;
import com.ocha.boc.response.UserUpdateResponse;
import com.ocha.boc.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> checkLogin(@RequestBody UserLoginRequest request) {
        UserLoginResponse response = userService.checkLogin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestParam String phone) {
        User user = userService.regiser(phone);
        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/update-information")
    public ResponseEntity<UserUpdateResponse> updateUserInformation(@RequestBody UserUpdateRequest request) {
        UserUpdateResponse response = userService.updateUserInformation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/deactive/{userId}")
    public ResponseEntity deActiveUser(@PathVariable(name = "userId") String userId) {
        boolean isDeActive = userService.deActiveUser(userId);
        if (isDeActive) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }
}
