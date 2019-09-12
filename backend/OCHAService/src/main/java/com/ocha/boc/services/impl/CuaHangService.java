package com.ocha.boc.services.impl;

import com.ocha.boc.dto.CuaHangDTO;
import com.ocha.boc.entity.CuaHang;
import com.ocha.boc.entity.User;
import com.ocha.boc.enums.UserType;
import com.ocha.boc.repository.CuaHangRepository;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.CuaHangRequest;
import com.ocha.boc.request.CuaHangUpdateRequest;
import com.ocha.boc.response.CuaHangResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CuaHangService {

    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private UserRepository userRepository;

    public CuaHangResponse createCuaHang(CuaHangRequest request) {
        CuaHangResponse response = new CuaHangResponse();
        response.setMessage(CommonConstants.CREATE_NEW_CUA_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (!Objects.isNull(request)) {
                if (StringUtils.isNotEmpty(request.getAddress()) && StringUtils.isNotEmpty(request.getCuaHangName())) {
                    if (!checkInforCuaHangIsExisted(request.getAddress(), request.getCuaHangName())) {
                        Optional<User> optOwner = userRepository.findUserByPhone(request.getPhone());
                        if (optOwner.isPresent()) {
                            CuaHang cuaHang = new CuaHang();
                            cuaHang.setCuaHangName(request.getCuaHangName());
                            cuaHang.setPhone(request.getPhone());
                            cuaHang.setManagerName(request.getManagerName());
                            cuaHang.setMoHinhKinhDoanhType(request.getMoHinhKinhDoanhType());
                            cuaHang.setDanhMucMatHangType(request.getDanhMucMatHangType());
                            cuaHang.setManagerPhone(request.getManagerPhone());
                            cuaHang.setCreatedDate(DateUtils.getCurrentDateAndTime());
                            cuaHang.setAddress(request.getAddress());
                            if (StringUtils.isNotEmpty(request.getManagerEmail())) {
                                cuaHang.setManagerEmail(request.getManagerEmail());
                            }
                            cuaHangRepository.save(cuaHang);
                            //Update cua hang id on table user
                            cuaHang = cuaHangRepository.findTopByOrderByCreatedDateDesc();
                            List<CuaHang> listCuaHang = optOwner.get().getListCuaHang();
                            listCuaHang.add(cuaHang);
                            optOwner.get().setListCuaHang(listCuaHang);
                            optOwner.get().setRole(UserType.ADMIN);
                            userRepository.save(optOwner.get());
                            //Check manager Phone exist in the system. If existed then assign cuaHangId to the account, if not
                            //Create new account with phone of the manager.
                            Optional<User> optManager = userRepository.findUserByPhone(request.getManagerPhone());
                            if (!optManager.isPresent()) {
                                User manager = new User();
                                manager.setPhone(request.getManagerPhone());
                                manager.setEmail(request.getManagerEmail());
                                manager.setName(request.getManagerName());
                                manager.setActive(Boolean.TRUE);
                                manager.setCreatedDate(Instant.now().toString());
                                manager.setRole(UserType.USER);
                            }
                            List<CuaHang> list = optManager.get().getListCuaHang();
                            list.add(cuaHang);
                            optManager.get().setListCuaHang(list);
                            userRepository.save(optManager.get());
                            response.setSuccess(Boolean.TRUE);
                            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                            response.setObject(new CuaHangDTO(cuaHang));
                        } else {
                            log.debug("Cannot find user by owner phone: ", request.getPhone());
                            response.setMessage(CommonConstants.UPDATE_CUA_HANG_ID_ON_USER_FAIL);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while createCuaHang: {}", e);
        }
        return response;
    }

    public CuaHangResponse updateEmailCuaHang(CuaHangUpdateRequest request) {
        CuaHangResponse response = new CuaHangResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_EMAIL_CUA_HANG_FAIL);
        try {
            if (!Objects.isNull(request)) {
                if (StringUtils.isNotEmpty(request.getId())) {
                    if (cuaHangRepository.existsById(request.getId())) {
                        Optional<CuaHang> optCuaHang = cuaHangRepository.findCuaHangById(request.getId());
                        if (StringUtils.isNotEmpty(request.getAddress())) {
                            optCuaHang.get().setAddress(request.getAddress());
                        }
                        if (StringUtils.isNotEmpty(request.getManagerEmail())) {
                            optCuaHang.get().setManagerEmail(request.getManagerEmail());
                        }
                        if (StringUtils.isNotEmpty(request.getMoHinhKinhDoanhType())) {
                            optCuaHang.get().setMoHinhKinhDoanhType(request.getMoHinhKinhDoanhType());
                        }
                        optCuaHang.get().setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                        cuaHangRepository.save(optCuaHang.get());
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new CuaHangDTO(optCuaHang.get()));
                        response.setSuccess(Boolean.TRUE);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while updateEmailCuaHang: {}", e);
        }
        return response;
    }


    private boolean checkInforCuaHangIsExisted(String location, String cuaHangName) {
        return cuaHangRepository.existsByAddressAndCuaHangName(location, cuaHangName);
    }

    public CuaHangResponse findCuaHangByCuaHangId(String cuaHangId) {
        CuaHangResponse response = new CuaHangResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CUA_HANG_IS_NOT_EXISTED);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                if (cuaHangRepository.existsById(cuaHangId)) {
                    Optional<CuaHang> optCuaHang = cuaHangRepository.findCuaHangById(cuaHangId);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new CuaHangDTO(optCuaHang.get()));
                }
            }
        } catch (Exception e) {
            log.error("Error when findCuaHangByCuaHangId: {}", e);
        }
        return response;
    }
}
