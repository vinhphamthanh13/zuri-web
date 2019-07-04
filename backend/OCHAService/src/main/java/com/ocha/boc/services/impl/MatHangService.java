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
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (request != null) {

                if (StringUtils.isNotEmpty(request.getName())) {
                    boolean isExisted = checkMatHangExisted(request.getName());
                    if (!isExisted) {
                        MatHang matHang = new MatHang();
                        matHang.setName(request.getName());
                        matHang.setBangGiaId(request.getBangGiaId());
                        matHang.setCreatedDate(Instant.now().toString());
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new MathangDTO(matHang));
                        matHangRepository.save(matHang);
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
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(request.getId())) {
                MatHang matHang = matHangRepository.findMatHangById(request.getId());
                if (matHang != null) {
                    matHang.setName(request.getName());
                    if(StringUtils.isNotEmpty(request.getBangGiaId())){
                        matHang.setBangGiaId(request.getBangGiaId());
                    }
                    matHang.setLastModifiedDate(Instant.now().toString());
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                    response.setObject(new MathangDTO(matHang));
                    matHangRepository.save(matHang);
                }
            }
        } catch (Exception e) {
            log.error("Error when updateMatHangInfor: {}", e);
        }
        return response;
    }

    public MatHangResponse findMatHangById(String id) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                MatHang matHang = matHangRepository.findMatHangById(id);
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

    public MatHangResponse getAllMatHang() {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            List<MatHang> matHangList = matHangRepository.findAll();
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

    public MatHangResponse deleteMatHangById(String id) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                MatHang matHang = matHangRepository.findMatHangById(id);
                if (matHang != null) {
                    matHangRepository.delete(matHang);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteMatHangById: {}", e);
        }
        return response;
    }

    private boolean checkMatHangExisted(String name) {
        boolean isExisted = false;
        try {
            MatHang matHang = matHangRepository.findMatHangByName(name);
            if (matHang != null) {
                isExisted = true;
            }
        } catch (Exception e) {
            log.error("Error when checkMatHangExisted: {}", e);
        }
        return isExisted;
    }

}
