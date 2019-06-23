package com.ocha.boc.services.impl;

import com.ocha.boc.entity.User;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserLoginResponse;
import com.ocha.boc.response.UserUpdateResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private static final String CURRENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS+07:00";

    @Autowired
    private UserRepository userRepository;

    public UserLoginResponse checkLogin(UserLoginRequest request) throws Exception {
        UserLoginResponse response = new UserLoginResponse();
        if (!StringUtils.isNotEmpty(request.getPhone())) {
            response.setSuccess(Boolean.FALSE);
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setObjectId(request.getPhone());
        } else {
            User user = userRepository.findUserByPhone(request.getPhone());
            if (user == null) {
                user = new User();
                user.setPhone(request.getPhone());
                user.setActive(Boolean.TRUE);
                String currentDate = getCurrentDate();
                if (StringUtils.isNotEmpty(currentDate)) {
                    long openDate = DateUtils.stringDateToLong(currentDate, null, CURRENT_DATE_FORMAT);
                    user.setOpenDate(openDate);
                } else {
                    log.error("Cannot get current date");
                }
                userRepository.save(user);
            }
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObjectId(request.getPhone());
            response.setObject(user);
        }
        return response;
    }


    public User regiser(String phone) {
        User user = null;
        try {
            if (StringUtils.isNotEmpty(phone)) {
                user = userRepository.findUserByPhone(phone);
                if (user != null) {
                    log.error("Phone number is existed. Cannot register new account");
                } else {
                    user = new User();
                    user.setPhone(phone);
                    String currentDate = getCurrentDate();
                    if (StringUtils.isNotEmpty(currentDate)) {
                        long openDate = DateUtils.stringDateToLong(currentDate, null, CURRENT_DATE_FORMAT);
                        user.setOpenDate(openDate);
                    } else {
                        log.error("Cannot get current date");
                    }
                    user.setActive(Boolean.TRUE);
                    userRepository.save(user);
                }
            }
        } catch (Exception e) {
            log.error("Cannot register new user with phone number " + phone + ". Error: " + e);
        }
        return user;
    }

    public UserUpdateResponse updateUserInformation(UserUpdateRequest request) {
        UserUpdateResponse response = new UserUpdateResponse();
        try {
            User user = checkUserExisted(request.getUserId());
            if (user == null) {
                response.setSuccess(Boolean.FALSE);
                response.setMessage("User is not existed. Cannot update user information");
                return response;
            }
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhoto(request.getPhoto());
            userRepository.save(user);
            response.setSuccess(Boolean.TRUE);
            response.setMessage("Update user successful");
            response.setObjectId(request.getUserId());
            response.setObject(user);
        } catch (Exception e) {
            log.error("Error when updateUserInformation: ", e);
        }
        return response;
    }

    public boolean deActiveUser(String userId) {
        boolean isDeActive = false;
        try {
            User user = userRepository.findUserById(userId);
            if (user != null) {
                user.setActive(Boolean.FALSE);
                isDeActive = true;
            }
        } catch (Exception e) {
            log.error("Error when deActiveUser: ", e);
        }
        return isDeActive;
    }

    private User checkUserExisted(String id) {
        User user = null;
        try {
            user = userRepository.findUserById(id);
        } catch (Exception e) {
            log.error("Error when checkUserExisted: ", e);
        }
        return user;
    }

    private String getCurrentDate() {
        String currentDate = null;
        try {
            currentDate = DateUtils.getCurrentDate();
        } catch (Exception e) {
            log.error("Error when getCurrentDate: ", e);
        }
        return currentDate;
    }

}
