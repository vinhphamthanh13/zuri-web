package com.ocha.boc.services.impl;

import com.authy.AuthyApiClient;
import com.authy.api.Params;
import com.authy.api.Verification;
import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.entity.User;
import com.ocha.boc.enums.UserType;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private AuthyApiClient authyApiClient;

    @Autowired
    public UserService(AuthyApiClient authyApiClient) {
        this.authyApiClient = authyApiClient;
    }

    public UserResponse newUser(UserLoginRequest request) {
        UserResponse response = new UserResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_USER_FAIL);
        try {
            Optional<User> optUser = userRepository.findUserByPhone(request.getPhone());
            if (!optUser.isPresent()) {
                User user = new User();
                user.setPhone(request.getPhone());
                user.setActive(Boolean.TRUE);
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
            log.error("Error when newUser: ", e);
        }
        return response;
    }

    public UserResponse updateUserInformation(UserUpdateRequest request) {
        UserResponse response = new UserResponse();
        response.setMessage(CommonConstants.UPDATE_USER_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            boolean isVerificationCodeSuccess = checkVerificationCode(request.getVerificationCode(), request.getCountryCode(), request.getPhoneNumber());
            if (!isVerificationCodeSuccess) {
                response.setMessage(CommonConstants.VERIFICATION_CODE_FAIL);
            } else {
                User user = checkUserExisted(request.getUserId());
                if (user != null) {
                    if (StringUtils.isNotEmpty(request.getEmail())) {
                        user.setEmail(request.getEmail());
                    }
                    if (StringUtils.isNotEmpty(request.getName())) {
                        user.setName(request.getName());
                    }
                    if (StringUtils.isNotEmpty(request.getPhoto())) {
                        user.setPhoto(request.getPhoto());
                    }
                    if (StringUtils.isNotEmpty(request.getRole().toString())) {
                        user.setRole(request.getRole());
                    }
                    user.setLastModifiedDate(Instant.now().toString());
                    userRepository.save(user);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new UserDTO(user));
                } else {
                    response.setMessage(CommonConstants.USER_IS_NULL);
                }
            }

        } catch (Exception e) {
            log.error("Error when updateUserInformation: ", e);
        }
        return response;
    }

    public UserResponse getAllUser() {
        UserResponse response = new UserResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_USER_FAIL);
        try {
            List<User> users = userRepository.getListUserActiveIsTrue();
            if (CollectionUtils.isNotEmpty(users)) {
                List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
                for (User user : users) {
                    listUserDTO.add(new UserDTO(user));
                }
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(listUserDTO);
                response.setTotalResultCount((long) listUserDTO.size());
            }
        } catch (Exception e) {
            log.error("Error when getAllUser: ", e);
        }
        return response;
    }

    public UserResponse getUserById(String userId) {
        UserResponse response = new UserResponse();
        response.setMessage(CommonConstants.USER_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(userId)) {
                Optional<User> user = userRepository.findUserById(userId);
                if (user.isPresent()) {
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new UserDTO(user.get()));
                }
            }
        } catch (Exception e) {
            log.error("Error when getUserById: ", e);
        }
        return response;
    }

    public AbstractResponse deActiveUser(String userId) {
        AbstractResponse response = new AbstractResponse();
        response.setMessage(CommonConstants.USER_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(userId)) {
                Optional<User> user = userRepository.findUserById(userId);
                if (user.isPresent()) {
                    user.get().setLastModifiedDate(Instant.now().toString());
                    user.get().setActive(Boolean.FALSE);
                    userRepository.save(user.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when deActiveUser: ", e);
        }
        return response;
    }

    private User checkUserExisted(String id) {
        Optional<User> user = null;
        try {
            user = userRepository.findUserById(id);
        } catch (Exception e) {
            log.error("Error when checkUserExisted: ", e);
        }
        return user.get();
    }

    public UserResponse activeUser(String userId) {
        UserResponse response = new UserResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.USER_IS_NULL);
        try {
            Optional<User> user = userRepository.findUserById(userId);
            if (user.isPresent()) {
                user.get().setLastModifiedDate(Instant.now().toString());
                user.get().setActive(Boolean.TRUE);
                userRepository.save(user.get());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new UserDTO(user.get()));
            }
        } catch (Exception e) {
            log.error("Error when activeUser: ", e);
        }
        return response;
    }

    public AbstractResponse sendVerificationCode(String countryCode, String phoneNumber) {
        AbstractResponse respsone = new AbstractResponse();
        respsone.setSuccess(Boolean.FALSE);
        respsone.setMessage(CommonConstants.SEND_VERIFICATION_CODE_FAIL);
        try {
            if (handlingSendVerificationCode(phoneNumber, countryCode)) {
                respsone.setSuccess(Boolean.TRUE);
                respsone.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            }
        } catch (Exception e) {
            log.error("Error when sendVerificationCode: ", e);
        }
        return respsone;
    }

    public UserResponse verifyUserCode(String countryCode, String phoneNumber, String token) {
        UserResponse response = new UserResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.VERIFICATION_CODE_FAIL);
        try {
            if (checkVerificationCode(token, countryCode, phoneNumber)) {
                Optional<User> user = userRepository.findUserByPhone(phoneNumber);
                if (user.isPresent()) {
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new UserDTO(user.get()));
                } else {
                    response.setMessage(CommonConstants.USER_NOT_EXISTED);
                }
            }
        } catch (Exception e) {
            log.error("Error when verifyUserCode: ", e);
        }
        return response;
    }


    private boolean handlingSendVerificationCode(String phoneNumber, String countryCode) throws Exception {
        boolean isSuccess = false;
        String via = "sms";
        Params params = new Params();
        params.setAttribute("locale", "vi");
        Verification verification = authyApiClient
                .getPhoneVerification()
                .start(phoneNumber, countryCode, via, params);
        if (!verification.isOk()) {
            logAndThrow("Error requesting phone verification. " +
                    verification.getMessage());
        } else {
            isSuccess = true;
        }
        return isSuccess;
    }


    private boolean checkVerificationCode(String token, String countryCode, String phoneNumber) throws Exception {
        boolean isSuccess = false;
        Verification verification = authyApiClient
                .getPhoneVerification()
                .check(phoneNumber, countryCode, token);
        if (verification.isOk()) {
            isSuccess = true;
        } else {
            logAndThrow("Error verifying token. " + verification.getMessage());
        }
        return isSuccess;
    }

    private void logAndThrow(String message) throws Exception {
        log.warn(message);
        throw new Exception(message);
    }

    public AbstractResponse findUserByPhoneNumber(String phoneNumber) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.USER_NOT_EXISTED);
        try {
            Optional<User> optUser = userRepository.findUserByPhone(phoneNumber);
            if(optUser.isPresent()){
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error("Exception while find user by phone number");
        }
        return response;
    }
}
