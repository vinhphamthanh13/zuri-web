package com.ocha.boc.services.impl;

import com.ocha.boc.entity.CuaHang;
import com.ocha.boc.entity.User;
import com.ocha.boc.repository.CuaHangRepository;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.CuaHangRequest;
import com.ocha.boc.request.CuaHangUpdateRequest;
import com.ocha.boc.response.CuaHangResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class CuaHangService {

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private UserRepository userRepository;

    public CuaHangResponse createCuaHang(CuaHangRequest request){
        CuaHangResponse response = new CuaHangResponse();
        response.setMessage(CommonConstants.CREATE_NEW_CUA_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        try{
            if(request!=null){
                boolean isExisted = checkInforCuaHangIsExisted(request.getPhone());
                if(!isExisted){
                    CuaHang cuaHang = new CuaHang();
                    cuaHang.setCuaHangName(request.getCuaHangName());
                    cuaHang.setPhone(request.getPhone());
                    cuaHang.setManagerName(request.getManagerName());
                    cuaHang.setMoHinhKinhDoanhType(request.getMoHinhKinhDoanhType());
                    cuaHang.setManagerPhone(request.getManagerPhone());
                    cuaHang.setCreatedDate(Instant.now().toString());
                    if(StringUtils.isNotEmpty(request.getAddress())){
                        cuaHang.setAddress(request.getAddress());
                    }
                    if(StringUtils.isNotEmpty(request.getManagerEmail())){
                        cuaHang.setManagerEmail(request.getManagerEmail());
                    }
                    cuaHangRepository.save(cuaHang);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    //Update cua hang id on table user
                    cuaHang = cuaHangRepository.findTopByOrderByCreatedDateDesc();
                    String cuaHangId = cuaHang.getId();
                    User user = userRepository.findUserByPhone(request.getPhone());
                    if(user != null){
                        user.setCuaHangId(cuaHangId);
                        userRepository.save(user);
                    }else{
                        log.error("Cannot find user by phone: ", request.getPhone());
                        response.setMessage(CommonConstants.UPDATE_CUA_HANG_ID_ON_USER_FAIL);
                    }
                }
            }
        }catch (Exception e){
            log.error("Error while createCuaHang: {}", e);
        }
        return response;
    }

    public CuaHangResponse updateEmailCuaHang(CuaHangUpdateRequest request){
        CuaHangResponse response = new CuaHangResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_EMAIL_CUA_HANG_FAIL);
        try{
            if(request!=null){
                if(StringUtils.isNotEmpty(request.getId())){
                    CuaHang cuaHang = cuaHangRepository.findCuaHangById(request.getId());
                    if(cuaHang != null){
                        if(StringUtils.isNotEmpty(request.getAddress())){
                            cuaHang.setAddress(request.getAddress());
                        }
                        if(StringUtils.isNotEmpty(request.getManagerEmail())){
                            cuaHang.setManagerEmail(request.getManagerEmail());
                        }
                        if(StringUtils.isNotEmpty(request.getMoHinhKinhDoanhType().label)){
                            cuaHang.setMoHinhKinhDoanhType(request.getMoHinhKinhDoanhType());
                        }
                        cuaHang.setLastModifiedDate(Instant.now().toString());
                        cuaHangRepository.save(cuaHang);
                    }
                }
            }
        }catch (Exception e){
            log.error("Error while updateEmailCuaHang: {}", e);
        }
        return response;
    }


    private boolean checkInforCuaHangIsExisted(String phone){
        boolean isExisted =false;
        CuaHang cuaHang = cuaHangRepository.findCuaHangByPhone(phone);
        if(cuaHang != null){
            isExisted = true;
        }
        return isExisted;
    }
}
