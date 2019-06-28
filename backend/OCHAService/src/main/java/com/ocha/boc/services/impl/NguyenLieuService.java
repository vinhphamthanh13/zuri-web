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

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NguyenLieuService {

    @Autowired
    private NguyenLieuRepository nguyenLieuRepository;

    public NguyenLieuResponse newNguyenLieu(NguyenLieuRequest request) {
        NguyenLieuResponse response = new NguyenLieuResponse();
        if (request == null) {
            response.setSuccess(Boolean.FALSE);
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            log.error("Error when create new NguyenLieu: Request body is null");
        } else {
            NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuByName(request.getName());
            if (nguyenLieu != null) {
                response.setSuccess(Boolean.FALSE);
                response.setMessage(CommonConstants.STR_FAIL_STATUS);
                log.error("Error when create new NguyenLieu: This Nguyen Lieu is existed in the system");
            } else {
                nguyenLieu = new NguyenLieu();
                nguyenLieu.setAbbreviations(request.getAbbreviations());
                nguyenLieu.setName(request.getName());
                nguyenLieu.setBangGiaId(request.getBangGiaId());
                nguyenLieuRepository.save(nguyenLieu);
            }
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setSuccess(Boolean.TRUE);
            response.setObject(new NguyenLieuDTO(nguyenLieu));
        }
        return response;
    }

    public NguyenLieuResponse updateNguyenLieuInformation(NguyenLieuRequest request) {
        NguyenLieuResponse response = new NguyenLieuResponse();
        if (request == null) {
            response.setSuccess(Boolean.FALSE);
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            log.error("Error while updateNguyenLieuInformation: Request body is null");
        } else {
            NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuByName(request.getName());
            if (nguyenLieu != null) {
                if (StringUtils.isNotEmpty(request.getAbbreviations())) {
                    nguyenLieu.setAbbreviations(request.getAbbreviations());
                }
                if (StringUtils.isNotEmpty(request.getName())) {
                    nguyenLieu.setName(request.getName());
                }
                if (StringUtils.isNotEmpty(request.getBangGiaId())) {
                    nguyenLieu.setBangGiaId(request.getBangGiaId());
                }
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new NguyenLieuDTO(nguyenLieu));
            } else {
                response.setSuccess(Boolean.FALSE);
                response.setMessage(CommonConstants.STR_FAIL_STATUS);
                log.error("Error while updateNguyenLieuInformation: This Nguyen Lieu is not existed in the system");
            }
        }
        return response;
    }

    public NguyenLieuResponse findNguyenLieuById(String id) {
        NguyenLieuResponse response = new NguyenLieuResponse();
        if (StringUtils.isNotEmpty(id)) {
            NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuById(id);
            if (nguyenLieu != null) {
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new NguyenLieuDTO(nguyenLieu));
            } else {
                response.setMessage(CommonConstants.STR_FAIL_STATUS);
                response.setSuccess(Boolean.FALSE);
                log.error("Error While findNguyenLieuById - Cannot find Nguyen Lieu with id: " + id);
            }
        } else {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            log.error("Error While findNguyenLieuById: id is empty or null");
        }
        return response;
    }

    public NguyenLieuResponse getAllNguyenLieu() {
        NguyenLieuResponse response = new NguyenLieuResponse();
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
        } else {
            response.setSuccess(Boolean.FALSE);
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            log.error("Error while getAllNguyenLieu");
        }
        return response;
    }

    public AbstractResponse deleteNguyenLieuById(String id) {
        AbstractResponse response = new AbstractResponse();
        NguyenLieu nguyenLieu = nguyenLieuRepository.findNguyenLieuById(id);
        if (nguyenLieu != null) {
            nguyenLieuRepository.delete(nguyenLieu);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setSuccess(Boolean.TRUE);
        } else {
            response.setSuccess(Boolean.FALSE);
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            log.error("Error when deleteNguyenLieuById: This Nguyen Lieu is not existed in the system");
        }
        return response;
    }
}
