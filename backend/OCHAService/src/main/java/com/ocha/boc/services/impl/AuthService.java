package com.ocha.boc.services.impl;

import com.authy.AuthyApiClient;
import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.entity.User;
import com.ocha.boc.enums.UserType;
import com.ocha.boc.error.ResourceNotFoundException;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.response.JwtAuthenticationResponse;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.security.jwt.JwtTokenProvider;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private AuthyApiClient authyApiClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public UserResponse register(UserLoginRequest request) {
        UserResponse response = new UserResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_USER_FAIL);
        try {
            Optional<User> optUser = userRepository.findUserByPhone(request.getPhone());
            if (!optUser.isPresent()) {
                User user = new User();
                user.setPhone(request.getPhone());
                user.setActive(Boolean.FALSE);
                user.setCreatedDate(Instant.now().toString());
                user.setRole(UserType.USER);
                userRepository.save(user);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjectId(user.getId());
                UserDTO userDTO = new UserDTO(user);
                response.setObject(userDTO);
            } else {
                response.setMessage(CommonConstants.USER_EXISTED);
            }
        } catch (Exception e) {
            log.error("Exception while register new user account: ", e);
        }
        return response;
    }

    public JwtAuthenticationResponse login(String phoneNumber){
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.LOGIN_FAIL);
        try{
            String jwt = tokenProvider.generateToken(phoneNumber);
            userRepository.findUserByPhoneAndIsActive(phoneNumber, true).map(user ->{
                user.setLastModifiedDate(Instant.now().toString());
                return userRepository.save(user);
            }).orElseThrow(
                    () -> new ResourceNotFoundException("User", CommonConstants.USER_NOT_EXISTED, phoneNumber));
            Optional<User> optUser = userRepository.findUserByPhoneAndIsActive(phoneNumber, true);
            if(optUser.isPresent()){
                if(optUser.get().isActive() == true){
                    response.setSuccess(Boolean.TRUE);
                    response.setAccessToken(jwt);
                }else{
                    response.setMessage(CommonConstants.ACCOUNT_IS_NOT_ACTIVE);
                }
            }
        }catch (Exception e){
            log.error("Exception while login into the system: ", e);
        }
        return response;
    }
}
