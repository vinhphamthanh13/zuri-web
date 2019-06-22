package com.ocha.boc.controllers;

import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.response.UserLoginResponse;
import com.ocha.boc.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> checkLogin(@RequestBody UserLoginRequest request){
        UserLoginResponse response = userService.checkLogin(request);
        return ResponseEntity.ok(response);
    }
}
