package com.ocha.boc.services.impl;

import com.ocha.boc.dto.MathangDTO;
import com.ocha.boc.entity.MatHang;
import com.ocha.boc.repository.MatHangRepository;
import com.ocha.boc.request.MatHangRequest;
import com.ocha.boc.request.MatHangUpdateRequest;
import com.ocha.boc.response.MatHangResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
                    boolean isExisted = checkMatHangExisted(request.getName(), request.getCuaHangId());
                    if (!isExisted) {
                        MatHang matHang = new MatHang();
                        matHang.setCuaHangId(request.getCuaHangId());
                        matHang.setName(request.getName());
                        matHang.setBangGiaId(request.getBangGiaId());
                        matHang.setDanhMucId(request.getDanhMucId());
                        if (StringUtils.isNotEmpty(request.getKhuyenMaiId())) {
                            matHang.setKhuyenMaiId(request.getKhuyenMaiId());
                        }
                        matHang.setCreatedDate(Instant.now().toString());
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new MathangDTO(matHang));
                        matHangRepository.save(matHang);
                    }else{
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
                MatHang matHang = matHangRepository.findMatHangByIdAndCuaHangId(request.getId(), request.getCuaHangId());
                if (matHang != null) {
                    matHang.setName(request.getName());
                    if (StringUtils.isNotEmpty(request.getBangGiaId())) {
                        matHang.setBangGiaId(request.getBangGiaId());
                    }
                    if (StringUtils.isNotEmpty(request.getDanhMucId())) {
                        matHang.setDanhMucId(request.getDanhMucId());
                    }
                    if (StringUtils.isNotEmpty(request.getKhuyenMaiId())) {
                        matHang.setKhuyenMaiId(request.getKhuyenMaiId());
                    }
                    matHang.setLastModifiedDate(Instant.now().toString());
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                    response.setObject(new MathangDTO(matHang));
                    matHangRepository.save(matHang);
                } else {
                    response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when updateMatHangInfor: {}", e);
        }
        return response;
    }

    public MatHangResponse findMatHangById(String cuaHangId,String id) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                MatHang matHang = matHangRepository.findMatHangByIdAndCuaHangId(id,cuaHangId);
                if (matHang != null) {
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new MathangDTO(matHang));
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

    public MatHangResponse deleteMatHangById(String cuaHangId,String id) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.DELETE_MAT_HANG_BY_MAT_HANG_ID_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                MatHang matHang = matHangRepository.findMatHangByIdAndCuaHangId(id, cuaHangId);
                if (matHang != null) {
                    matHangRepository.delete(matHang);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }else{
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
            MatHang matHang = matHangRepository.findMatHangByNameAndCuaHangId(name, cuaHangId);
            if (matHang != null) {
                isExisted = true;
            }
        } catch (Exception e) {
            log.error("Error when checkMatHangExisted: {}", e);
        }
        return isExisted;
    }

}
