package com.ocha.boc.services.impl;

import com.authy.AuthyApiClient;
import com.authy.api.Verification;
import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.entity.User;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public UserResponse updateUserInformation(UserUpdateRequest request) {
        UserResponse response = new UserResponse();
        response.setMessage(CommonConstants.UPDATE_USER_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            boolean isVerificationCodeSuccess = checkVerificationCode(request.getVerificationCode(), request.getCountryCode(), request.getPhoneNumber());
            if (!isVerificationCodeSuccess) {
                response.setMessage(CommonConstants.VERIFICATION_CODE_FAIL);
                return response;
            }
            if (!checkUserExisted(request.getUserId())) {
                response.setMessage(CommonConstants.USER_IS_NULL);
                return response;
            }
            userRepository.findUserById(request.getUserId()).map(user -> {
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
                response.setObject(new UserDTO(user));
                return userRepository.save(user);
            });
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
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
//                List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
//                for (User user : users) {
//                    listUserDTO.add(new UserDTO(user));
//                }
                List<UserDTO> result = users.stream().map(UserDTO::new).collect(Collectors.toList());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setTotalResultCount((long) result.size());
                response.setObjects(result);
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
                if (userRepository.existsById(userId)) {
                    Optional<User> user = userRepository.findUserById(userId);
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
                if (userRepository.existsById(userId)) {
                    Optional<User> user = userRepository.findUserById(userId);
                    user.get().setLastModifiedDate(Instant.now().toString());
                    user.get().setActive(Boolean.FALSE);
                    userRepository.save(user.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new UserDTO(user.get()));
                }
            }
        } catch (Exception e) {
            log.error("Error when deActiveUser: ", e);
        }
        return response;
    }

    private boolean checkUserExisted(String id) {
        return userRepository.existsById(id);
    }

    public UserResponse activeUser(String userId) {
        UserResponse response = new UserResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.USER_IS_NULL);
        try {
            if (StringUtils.isNotEmpty(userId)) {
                if (userRepository.existsById(userId)) {
                    Optional<User> user = userRepository.findUserById(userId);
                    user.get().setLastModifiedDate(Instant.now().toString());
                    user.get().setActive(Boolean.TRUE);
                    userRepository.save(user.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new UserDTO(user.get()));
                }
            }
        } catch (Exception e) {
            log.error("Error when activeUser: ", e);
        }
        return response;
    }

    private boolean checkVerificationCode(String token, String countryCode, String phoneNumber) throws Exception {
        Verification verification = authyApiClient
                .getPhoneVerification()
                .check(phoneNumber, countryCode, token);
        if (!verification.isOk()) {
            logAndThrow("Error verifying token. " + verification.getMessage());
            return false;
        }
        return true;
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
            if (userRepository.existsByPhone(phoneNumber)) {
                Optional<User> optUser = userRepository.findUserByPhone(phoneNumber);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setObject(new UserDTO(optUser.get()));
            }
        } catch (Exception e) {
            log.error("Exception while find user by phone number");
        }
        return response;
    }

    public AbstractResponse deleteUserByPhoneNumber(String phoneNumber) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.USER_NOT_EXISTED);
        try {
            if (userRepository.existsByPhone(phoneNumber)) {
                Optional<User> optUser = userRepository.findUserByPhone(phoneNumber);
                userRepository.delete(optUser.get());
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error("Exception while delete user by phone number");
        }
        return response;
    }
}
