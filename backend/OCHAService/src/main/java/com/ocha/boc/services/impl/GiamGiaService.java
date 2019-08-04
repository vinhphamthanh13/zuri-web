package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.GiamGiaDTO;
import com.ocha.boc.entity.GiamGia;
import com.ocha.boc.enums.GiamGiaType;
import com.ocha.boc.repository.GiamGiaRepository;
import com.ocha.boc.request.GiamGiaRequest;
import com.ocha.boc.request.GiamGiaUpdateRequest;
import com.ocha.boc.response.GiamGiaResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class GiamGiaService {

    @Autowired
    private GiamGiaRepository giamGiaRepository;

    public GiamGiaResponse createNewGiamGia(GiamGiaRequest request) {
        GiamGiaResponse response = new GiamGiaResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_GIAM_GIA_FAIL);
        try {
            if (request != null) {
                GiamGia giamGia = new GiamGia();
                giamGia.setCuaHangId(request.getCuaHangId());
                giamGia.setName(request.getName());
                if (StringUtils.isNotEmpty(request.getDanhMucId())) {
                    giamGia.setDanhMucId(request.getDanhMucId());
                }
                if (request.getDiscountAmount() != null) {
                    giamGia.setDiscountAmount(request.getDiscountAmount());
                }
                if (request.getPercentage() != null) {
                    giamGia.setPercentage(request.getPercentage());
                }
                giamGia.setCreatedDate(Instant.now().toString());
                giamGia.setGiamGiaType(request.getGiamGiaType());
                giamGiaRepository.save(giamGia);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new GiamGiaDTO(giamGia));
            }
        } catch (Exception e) {
            log.error("Error when create new giam gia: {}", e);
        }
        return response;
    }

    public AbstractResponse deleteGiamGiaByGiamGiaId(String giamGiaId) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_GIAM_GIA_BY_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(giamGiaId)) {
                GiamGia giamGia = giamGiaRepository.findGiamGiaById(giamGiaId);
                if (giamGia != null) {
                    giamGiaRepository.delete(giamGia);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when delete giam gia: {}", e);
        }
        return response;
    }

    public GiamGiaResponse updateGiamGia(GiamGiaUpdateRequest request) {
        GiamGiaResponse response = new GiamGiaResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_GIAM_GIA_FAIL);
        try {
            if (request != null) {
                if (StringUtils.isNotEmpty(request.getGiamGiaId())) {
                    GiamGia giamGia = giamGiaRepository.findGiamGiaById(request.getGiamGiaId());
                    if (giamGia != null) {
                        giamGia.setName(request.getName());
                        giamGia.setLastModifiedDate(Instant.now().toString());
                        if (request.getGiamGiaType().label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THEO_DANH_MỤC.label.toString())) {
                            if (request.getPercentage() != null) {
                                giamGia.setPercentage(request.getPercentage());
                            }
                            if (StringUtils.isNotEmpty(request.getDanhMucId())) {
                                giamGia.setDanhMucId(request.getDanhMucId());
                            }

                        } else if (request.getGiamGiaType().label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THÔNG_THƯỜNG.label.toString())) {
                            if (request.getPercentage() != null) {
                                giamGia.setPercentage(request.getPercentage());
                                giamGia.setDiscountAmount(null);
                            }
                            if (request.getDiscountAmount() != null) {
                                giamGia.setDiscountAmount(request.getDiscountAmount());
                                giamGia.setPercentage(null);
                            }
                        }

                        giamGia.setGiamGiaType(request.getGiamGiaType());
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setSuccess(Boolean.TRUE);
                        response.setObject(new GiamGiaDTO(giamGia));
                        giamGiaRepository.save(giamGia);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when updateGiamGiaByGiamGiaId: {}", e);
        }
        return response;
    }
}
