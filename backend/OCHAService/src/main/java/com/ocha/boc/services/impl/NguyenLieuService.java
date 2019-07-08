package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.NguyenLieuDTO;
import com.ocha.boc.entity.NguyenLieu;
import com.ocha.boc.repository.NguyenLieuRepository;
import com.ocha.boc.request.NguyenLieuRequest;
import com.ocha.boc.response.NguyenLieuResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NguyenLieuService {

    @Autowired
    private NguyenLieuRepository nguyenLieuRepository;

    public NguyenLieuResponse newNguyenLieu(NguyenLieuRequest request) {
        NguyenLieuResponse response = new NguyenLieuResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_NGUYEN_LIEU_FAIL);
        try {
            NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuByName(request.getName());
            if (nguyenLieu != null) {
                response.setMessage(CommonConstants.NGUYEN_LIEU_EXISTED);
                log.error("Error when create new NguyenLieu: This Nguyen Lieu is existed in the system");
            } else {
                nguyenLieu = new NguyenLieu();
                nguyenLieu.setAbbreviations(request.getAbbreviations());
                nguyenLieu.setName(request.getName());
                nguyenLieu.setCreatedDate(Instant.now().toString());
                nguyenLieuRepository.save(nguyenLieu);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setObject(new NguyenLieuDTO(nguyenLieu));
            }
        } catch (Exception e) {
            log.error("Error when newNguyenLieu: ", e);
        }
        return response;
    }

    public NguyenLieuResponse updateNguyenLieuInformation(NguyenLieuRequest request) {
        NguyenLieuResponse response = new NguyenLieuResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_NGUYEN_LIEU_FAIL);
        try {
            NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuByName(request.getName());
            if (nguyenLieu != null) {
                if (StringUtils.isNotEmpty(request.getAbbreviations())) {
                    nguyenLieu.setAbbreviations(request.getAbbreviations());
                }
                if (StringUtils.isNotEmpty(request.getName())) {
                    nguyenLieu.setName(request.getName());
                }
                nguyenLieu.setLastModifiedDate(Instant.now().toString());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new NguyenLieuDTO(nguyenLieu));
                nguyenLieuRepository.save(nguyenLieu);
            } else {
                response.setMessage(CommonConstants.NGUYEN_LIEU_IS_NULL);
                log.error("Error while updateNguyenLieuInformation: This Nguyen Lieu is not existed in the system");
            }
        } catch (Exception e) {
            log.error("Error when updateNguyenLieuInformation: ", e);
        }
        return response;
    }

    public NguyenLieuResponse findNguyenLieuById(String id) {
        NguyenLieuResponse response = new NguyenLieuResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.NGUYEN_LIEU_IS_NULL);
        try {
            if (StringUtils.isNotEmpty(id)) {
                NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuById(id);
                if (nguyenLieu != null) {
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new NguyenLieuDTO(nguyenLieu));
                }
            }
        } catch (Exception e) {
            log.error("Error when findNguyenLieuById: ", e);
        }
        return response;
    }

    public NguyenLieuResponse getAllNguyenLieu() {
        NguyenLieuResponse response = new NguyenLieuResponse();
        response.setMessage(CommonConstants.GET_ALL_NGUYEN_LIEU_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            List<NguyenLieu> nguyenLieuList = nguyenLieuRepository.findAll();
            if (nguyenLieuList.size() > 0) {
                List<NguyenLieuDTO> nguyenLieuDTOList = new ArrayList<>();
                for (NguyenLieu nguyenLieu : nguyenLieuList) {
                    NguyenLieuDTO nguyenLieuDTO = new NguyenLieuDTO(nguyenLieu);
                    nguyenLieuDTOList.add(nguyenLieuDTO);
                }
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setObjects(nguyenLieuDTOList);
            }
        } catch (Exception e) {
            log.error("Error when getAllNguyenLieu: ", e);
        }
        return response;
    }

    public AbstractResponse deleteNguyenLieuById(String id) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_NGUYEN_LIEU_BY_NGUYEN_LIEU_ID_FAIL);
        try {
            NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuById(id);
            if (nguyenLieu != null) {
                nguyenLieuRepository.delete(nguyenLieu);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            } else {
                response.setMessage(CommonConstants.NGUYEN_LIEU_IS_NULL);
            }
        } catch (Exception e) {
            log.error("Error when deleteNguyenLieuById: ", e);
        }
        return response;
    }
}
