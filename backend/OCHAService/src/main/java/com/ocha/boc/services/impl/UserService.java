package com.ocha.boc.services.impl;

import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.entity.User;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.request.UserUpdateRequest;
import com.ocha.boc.response.UserResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
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

    public UserResponse newUser(UserLoginRequest request) {
        UserResponse response = new UserResponse();
        if (!StringUtils.isNotEmpty(request.getPhone())) {
            response.setSuccess(Boolean.FALSE);
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
        } else {
            User user = userRepository.findUserByPhone(request.getPhone());
            if (user == null) {
                user = new User();
                user.setPhone(request.getPhone());
                user.setActive(Boolean.TRUE);
                user.setCreatedDate(Instant.now().toString());
                user.setLastModifiedDate(Instant.now().toString());
                userRepository.save(user);
            }
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObjectId(user.getId());
            UserDTO userDTO = new UserDTO(user);
            response.setObject(userDTO);
        }
        return response;
    }

    public UserResponse updateUserInformation(UserUpdateRequest request) {
        UserResponse response = new UserResponse();
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
            user.setLastModifiedDate(Instant.now().toString());
            userRepository.save(user);
            response.setSuccess(Boolean.TRUE);
            response.setMessage("Update user successful");
            response.setObjectId(user.getId());
            UserDTO userDTO = new UserDTO(user);
            response.setObject(userDTO);
        } catch (Exception e) {
            response.setSuccess(Boolean.FALSE);
            log.error("Error when updateUserInformation: ", e);
            response.setMessage("updateUserInformation is FAILED");
        }
        return response;
    }

    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtoList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO(user);
            dtoList.add(userDTO);
        }
        return dtoList;
    }

    public User getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;
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
}
