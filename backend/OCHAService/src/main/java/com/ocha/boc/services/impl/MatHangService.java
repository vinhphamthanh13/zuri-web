package com.ocha.boc.services.impl;

import com.ocha.boc.dto.MathangDTO;
import com.ocha.boc.entity.MatHang;
import com.ocha.boc.repository.MatHangRepository;
import com.ocha.boc.request.MatHangRequest;
import com.ocha.boc.request.MatHangUpdateRequest;
import com.ocha.boc.response.MatHangResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MatHangService {

    @Autowired
    private MatHangRepository matHangRepository;

    public MatHangResponse createNewMatHang(MatHangRequest request) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.CREATE_NEW_MAT_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (request != null) {
                if (StringUtils.isNotEmpty(request.getName())) {
                    if (!checkMatHangExisted(request.getName(), request.getCuaHangId())) {
                        MatHang matHang = new MatHang();
                        matHang.setCuaHangId(request.getCuaHangId());
                        matHang.setName(request.getName());
                        matHang.setDanhMucId(request.getDanhMucId());
                        if (CollectionUtils.isNotEmpty(request.getListBangGia())) {
                            matHang.setListBangGia(request.getListBangGia());
                        }
                        matHang.setCreatedDate(DateUtils.getCurrentDateAndTime());
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new MathangDTO(matHang));
                        matHangRepository.save(matHang);
                    } else {
                        response.setMessage(CommonConstants.MAT_HANG_IS_EXISTED);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when createNewMatHang: {}", e);
        }
        return response;
    }

    public MatHangResponse updateMatHangInfor(MatHangUpdateRequest request) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.UPDATE_MAT_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(request.getId())) {
                Optional<MatHang> optMatHang = matHangRepository.findMatHangByIdAndCuaHangId(request.getId(), request.getCuaHangId());
                if (optMatHang.isPresent()) {
                    optMatHang.get().setName(request.getName());
                    if (CollectionUtils.isNotEmpty(request.getListBangGia())) {
                        optMatHang.get().setListBangGia(request.getListBangGia());
                    }
                    if (StringUtils.isNotEmpty(request.getDanhMucId())) {
                        optMatHang.get().setDanhMucId(request.getDanhMucId());
                    }
                    optMatHang.get().setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                    response.setObject(new MathangDTO(optMatHang.get()));
                    matHangRepository.save(optMatHang.get());
                } else {
                    response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when updateMatHangInfor: {}", e);
        }
        return response;
    }

    public MatHangResponse findMatHangById(String cuaHangId, String id) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                Optional<MatHang> optMatHang = matHangRepository.findMatHangByIdAndCuaHangId(id, cuaHangId);
                if (optMatHang.isPresent()) {
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new MathangDTO(optMatHang.get()));
                }
            }
        } catch (Exception e) {
            log.error("Error when findMatHangById: {}", e);
        }
        return response;
    }

    public MatHangResponse getAllMatHang(String cuaHangId) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.GET_ALL_MAT_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            List<MatHang> matHangList = matHangRepository.findAllByCuaHangId(cuaHangId);
            if (CollectionUtils.isNotEmpty(matHangList)) {
                List<MathangDTO> mathangDTOList = new ArrayList<>();
                for (MatHang matHang : matHangList) {
                    MathangDTO temp = new MathangDTO(matHang);
                    mathangDTOList.add(temp);
                }
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setTotalResultCount((long) matHangList.size());
                response.setObjects(mathangDTOList);
            }
        } catch (Exception e) {
            log.error("Error when getAllMatHang: {}", e);
        }
        return response;
    }

    public MatHangResponse deleteMatHangById(String cuaHangId, String id) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.DELETE_MAT_HANG_BY_MAT_HANG_ID_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                Optional<MatHang> optMatHang = matHangRepository.findMatHangByIdAndCuaHangId(id, cuaHangId);
                if (optMatHang.isPresent()) {
                    matHangRepository.delete(optMatHang.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                } else {
                    response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteMatHangById: {}", e);
        }
        return response;
    }

    private boolean checkMatHangExisted(String name, String cuaHangId) {
        boolean isExisted = false;
        try {
            Optional<MatHang> optMatHang = matHangRepository.findMatHangByNameAndCuaHangId(name, cuaHangId);
            if (optMatHang.isPresent()) {
                isExisted = true;
            }
        } catch (Exception e) {
            log.error("Error when checkMatHangExisted: {}", e);
        }
        return isExisted;
    }

}
