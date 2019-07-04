package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.BangGiaDetailDTO;
import com.ocha.boc.entity.BangGia;
import com.ocha.boc.entity.BangGiaDetail;
import com.ocha.boc.repository.BangGiaDetailRepository;
import com.ocha.boc.repository.BangGiaRepository;
import com.ocha.boc.request.BangGiaDetailRequest;
import com.ocha.boc.request.BangGiaDetailUpdateRequest;
import com.ocha.boc.response.BangGiaDetailResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BangGiaDetailService {

    @Autowired
    private BangGiaDetailRepository bangGiaDetailRepository;

    @Autowired
    private BangGiaRepository bangGiaRepository;

    public BangGiaDetailResponse createNewBangGiaDetail(BangGiaDetailRequest request) {
        BangGiaDetailResponse response = new BangGiaDetailResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (request != null) {
                String bangGiaId = request.getBangGiaId();
                if (StringUtils.isNotEmpty(bangGiaId)) {
                    BangGia bangGia = bangGiaRepository.findBangGiaByBangGiaId(bangGiaId);
                    if (bangGia != null) {
                        int numberOfPriceInBangGia = bangGia.getNumberOfPrice();
                        Integer sumOfPriceInBangGiaDetail = bangGiaDetailRepository.countBangGiaDetailByBangGiaId(request.getBangGiaId());
                        if (sumOfPriceInBangGiaDetail >= numberOfPriceInBangGia) {
                            response.setSuccess(Boolean.FALSE);
                            response.setMessage("This BangGia has enough price. Cannot add anymore!");
                        } else {
                            if (request.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                                BangGiaDetail bangGiaDetail = new BangGiaDetail();
                                bangGiaDetail.setBangGiaId(bangGiaId);
                                bangGiaDetail.setPrice(request.getPrice());
                                bangGiaDetail.setCreatedDate(Instant.now().toString());
                                bangGiaDetailRepository.save(bangGiaDetail);
                                response.setSuccess(Boolean.TRUE);
                                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                                response.setObject(new BangGiaDetailDTO(bangGiaDetail));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when createNewBangGiaDetail: {}", e);
        }
        return response;
    }

    public BangGiaDetailResponse updateBangGiaDetail(BangGiaDetailUpdateRequest request) {
        BangGiaDetailResponse response = new BangGiaDetailResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (request != null) {
                if (StringUtils.isNotEmpty(request.getId())) {
                    BangGiaDetail bangGiaDetail = bangGiaDetailRepository.findBangGiaDetailById(request.getId());
                    if (bangGiaDetail != null) {
                        bangGiaDetail.setPrice(request.getPrice());
                        bangGiaDetail.setLastModifiedDate(Instant.now().toString());
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setSuccess(Boolean.TRUE);
                        response.setObject(new BangGiaDetailDTO(bangGiaDetail));
                        bangGiaDetailRepository.save(bangGiaDetail);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when updateBangGiaDetail: {}", e);
        }
        return response;
    }

    public BangGiaDetailResponse findBangGiaDetailById(String id) {
        BangGiaDetailResponse response = new BangGiaDetailResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                BangGiaDetail bangGiaDetail = bangGiaDetailRepository.findBangGiaDetailById(id);
                if (bangGiaDetail != null) {
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new BangGiaDetailDTO(bangGiaDetail));
                }
            }
        } catch (Exception e) {
            log.error("Error when findBangGiaDetailById: {}", e);
        }
        return response;
    }

    public BangGiaDetailResponse findBangGiaDetailByBangGiaId(String bangGiaId) {
        BangGiaDetailResponse response = new BangGiaDetailResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(bangGiaId)) {
                List<BangGiaDetail> bangGiaDetailList = bangGiaDetailRepository.findBangGiaDetailByBangGiaId(bangGiaId);
                List<BangGiaDetailDTO> bangGiaDetailDTOList = convertListBangGiaDetailToListBangGiaDetailDTO(bangGiaDetailList);
                if (bangGiaDetailDTOList.size() > 0) {
                    response.setObjects(bangGiaDetailDTOList);
                    response.setTotalResultCount((long) bangGiaDetailDTOList.size());
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when findBangGiaDetailByBangGiaId: {}", e);
        }
        return response;
    }

    public BangGiaDetailResponse getAllBangGiaDetail() {
        BangGiaDetailResponse response = new BangGiaDetailResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            List<BangGiaDetail> bangGiaDetailList = bangGiaDetailRepository.findAll();
            List<BangGiaDetailDTO> bangGiaDetailDTOList = convertListBangGiaDetailToListBangGiaDetailDTO(bangGiaDetailList);
            if (bangGiaDetailDTOList.size() > 0) {
                response.setObjects(bangGiaDetailDTOList);
                response.setTotalResultCount((long) bangGiaDetailDTOList.size());
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error("Error when getAllBangGiaDetail: {}", e);
        }
        return response;
    }

    public AbstractResponse<String, BangGiaDetailDTO> deleteBangGiaDetailById(String id) {
        AbstractResponse response = new AbstractResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                BangGiaDetail bangGiaDetail = bangGiaDetailRepository.findBangGiaDetailById(id);
                if (bangGiaDetail != null) {
                    bangGiaDetailRepository.delete(bangGiaDetail);
                    response.setSuccess(Boolean.FALSE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteBangGiaDetailById: {}", e);
        }
        return response;
    }

    private List<BangGiaDetailDTO> convertListBangGiaDetailToListBangGiaDetailDTO(List<BangGiaDetail> bangGiaDetailList) {
        List<BangGiaDetailDTO> result = new ArrayList<>();
        if (bangGiaDetailList.size() > 0) {
            for (BangGiaDetail bangGiaDetail : bangGiaDetailList) {
                result.add(new BangGiaDetailDTO(bangGiaDetail));
            }
        }
        return result;
    }

}
