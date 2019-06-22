package com.ocha.boc.services.impl;

import com.ocha.boc.entity.User;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.UserLoginRequest;
import com.ocha.boc.response.UserLoginResponse;
import com.ocha.boc.util.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserLoginResponse checkLogin(UserLoginRequest request){
        UserLoginResponse response = new UserLoginResponse();
        if(!StringUtils.isNotEmpty(request.getPhone())){
            response.setSuccess(Boolean.FALSE);
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setObjectId(request.getPhone());
        }else{
            User user = userRepository.findByPhone(request.getPhone());
            if(user == null){
                response.setSuccess(Boolean.FALSE);
                response.setMessage(CommonConstants.STR_FAIL_STATUS);
                response.setObjectId(request.getPhone());
            }else{
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjectId(request.getPhone());
            }
        }
        return response;
    }
}
