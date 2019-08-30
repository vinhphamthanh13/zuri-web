package com.ocha.boc.services.impl;

import com.authy.AuthyApiClient;
import com.authy.api.Params;
import com.authy.api.Verification;
import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.entity.User;
import com.ocha.boc.enums.UserType;
import com.ocha.boc.error.ResourceNotFoundException;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.OTPRequest;
import com.ocha.boc.request.SendOTPRequest;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.response.JwtAuthenticationResponse;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.security.jwt.JwtTokenProvider;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthyApiClient authyApiClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Value(value = "${base64.secret}")
    private String baseSecretKey;

    public UserResponse register(UserLoginRequest request) {
        UserResponse response = new UserResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_USER_FAIL);
        try {
            if(StringUtils.isNotEmpty(request.getPhone())){
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
            }
        } catch (Exception e) {
            log.error("Exception while register new user account: ", e);
        }
        return response;
    }

    public JwtAuthenticationResponse login(OTPRequest request) {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.LOGIN_FAIL);
        try {
            if (!Objects.isNull(request)) {
                if (StringUtils.isNotEmpty(request.getCountryCode()) && StringUtils.isNotEmpty(request.getOptCode()) && StringUtils.isNotEmpty(request.getPhoneNumber())) {
                    if (checkVerificationCode(request.getOptCode(), request.getCountryCode(), request.getPhoneNumber())) {
                        String jwt = tokenProvider.generateToken(request.getPhoneNumber());
                        Optional<User> optUser = userRepository.findUserByPhoneAndIsActive(request.getPhoneNumber(), true);
                        if (optUser.isPresent()) {
                            if (optUser.get().isActive() == true) {
                                optUser.get().setLastModifiedDate(Instant.now().toString());
                                userRepository.save(optUser.get());
                                response.setSuccess(Boolean.TRUE);
                                response.setAccessToken(jwt);
                                response.setObject(new UserDTO(optUser.get()));
                                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                            } else {
                                response.setMessage(CommonConstants.ACCOUNT_IS_NOT_ACTIVE);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception while login into the system: ", e);
        }
        return response;
    }

    public AbstractResponse sendOTP(SendOTPRequest request) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.SEND_VERIFICATION_CODE_FAIL);
        try {
            if (!Objects.isNull(request)) {
                if (StringUtils.isNotEmpty(request.getCountryCode()) && StringUtils.isNotEmpty(request.getPhoneNumber())) {
                    if (handlingSendVerificationCode(request.getPhoneNumber(), request.getCountryCode())) {
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception while sending OTP: ", e);
        }
        return response;
    }

    public JwtAuthenticationResponse verifyOTP(OTPRequest request) {
        JwtAuthenticationResponse response = new JwtAuthenticationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.VERIFICATION_CODE_FAIL);
        try {
            if (!Objects.isNull(request)) {
                if (StringUtils.isNotEmpty(request.getCountryCode()) && StringUtils.isNotEmpty(request.getOptCode()) && StringUtils.isNotEmpty(request.getPhoneNumber())) {
                    if (checkVerificationCode(request.getOptCode(), request.getCountryCode(), request.getPhoneNumber())) {
                        Optional<User> optUser = userRepository.findUserByPhone(request.getPhoneNumber());
                        if (optUser.isPresent()) {
                            String jwt = tokenProvider.generateToken(request.getPhoneNumber());
                            userRepository.findUserByPhone(request.getPhoneNumber()).map(user -> {
                                user.setActive(Boolean.TRUE);
                                return userRepository.save(user);
                            });
                            response.setObject(new UserDTO(optUser.get()));
                            response.setAccessToken(jwt);
                            response.setSuccess(Boolean.TRUE);
                            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Exception while verify OTP: ", e);
        }
        return response;
    }

    private boolean handlingSendVerificationCode(String phoneNumber, String countryCode) throws Exception {
        boolean isSuccess = false;
        String via = "sms";
        Params params = new Params();
        params.setAttribute("locale", "vi");
        Verification verification = authyApiClient.getPhoneVerification()
                .start(phoneNumber, countryCode, via, params);
        if (!verification.isOk()) {
            logAndThrow("Error requesting phone verification. " +
                    verification.getMessage());
        } else {
            isSuccess = true;
        }
        return isSuccess;
    }

    private void logAndThrow(String message) throws Exception {
        log.warn(message);
        throw new Exception(message);
    }

    private boolean checkVerificationCode(String token, String countryCode, String phoneNumber) throws Exception {
        boolean isSuccess = false;
        Verification verification = authyApiClient
                .getPhoneVerification()
                .check(phoneNumber, countryCode, token);
        if (verification.isOk()) {
            isSuccess = true;
        }
        return isSuccess;
    }
}
