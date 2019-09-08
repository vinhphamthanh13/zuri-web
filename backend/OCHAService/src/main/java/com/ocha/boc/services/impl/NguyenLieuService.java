package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.NguyenLieuDTO;
import com.ocha.boc.entity.NguyenLieu;
import com.ocha.boc.repository.NguyenLieuRepository;
import com.ocha.boc.request.NguyenLieuRequest;
import com.ocha.boc.response.NguyenLieuResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            if (nguyenLieuRepository.existsByName(request.getName())) {
                response.setMessage(CommonConstants.NGUYEN_LIEU_EXISTED);
                log.error("Error when create new NguyenLieu: This Nguyen Lieu is existed in the system");
            } else {
                NguyenLieu nguyenLieu = new NguyenLieu();
                nguyenLieu.setAbbreviations(request.getAbbreviations());
                nguyenLieu.setName(request.getName());
                nguyenLieu.setCreatedDate(DateUtils.getCurrentDateAndTime());
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

    @CachePut(value = "nguyenlieu", key = "#request.name")
    public NguyenLieu updateNguyenLieuInformation(NguyenLieuRequest request) {
        try {
            if (nguyenLieuRepository.existsByName(request.getName())) {
                Optional<NguyenLieu> optNguyenLieu = nguyenLieuRepository.findNguyenLieuByName(request.getName());
                if (StringUtils.isNotEmpty(request.getAbbreviations())) {
                    optNguyenLieu.get().setAbbreviations(request.getAbbreviations());
                }
                if (StringUtils.isNotEmpty(request.getName())) {
                    optNguyenLieu.get().setName(request.getName());
                }
                optNguyenLieu.get().setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                nguyenLieuRepository.save(optNguyenLieu.get());
            }
        } catch (Exception e) {
            log.error("Error when updateNguyenLieuInformation: ", e);
        }
        return null;
    }

    @Cacheable(value = "nguyenlieu", key = "#id")
    public NguyenLieu findNguyenLieuById(String id) {
        try {
            if (StringUtils.isNotEmpty(id)) {
                if (nguyenLieuRepository.existsById(id)) {
                    Optional<NguyenLieu> optNguyenLieu = nguyenLieuRepository.findNguyenLieuById(id);
                    return optNguyenLieu.get();
                }
            }
        } catch (Exception e) {
            log.error("Error when findNguyenLieuById: ", e);
        }
        return null;
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

    @CacheEvict(value = "nguyenlieu", key = "#id")
    public AbstractResponse deleteNguyenLieuById(String id) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_NGUYEN_LIEU_BY_NGUYEN_LIEU_ID_FAIL);
        try {
            if (nguyenLieuRepository.existsById(id)) {
                Optional<NguyenLieu> optNguyenLieu = nguyenLieuRepository.findNguyenLieuById(id);
                nguyenLieuRepository.delete(optNguyenLieu.get());
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
