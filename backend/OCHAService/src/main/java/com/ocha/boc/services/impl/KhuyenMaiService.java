package com.ocha.boc.services.impl;

import com.ocha.boc.dto.KhuyenMaiDTO;
import com.ocha.boc.entity.KhuyenMai;
import com.ocha.boc.repository.KhuyenMaiRepository;
import com.ocha.boc.request.KhuyenMaiRequest;
import com.ocha.boc.request.KhuyenMaiUpdateRequest;
import com.ocha.boc.response.KhuyenMaiResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class KhuyenMaiService {

    private static final String NUMBER_ONE = "1";
    private static final String FORMAT_DATE = "yyyy-MM-dd";

    @Autowired
    private KhuyenMaiRepository khuyenMaiRepository;


    public KhuyenMaiResponse createNewKhuyenMai(KhuyenMaiRequest request) {
        KhuyenMaiResponse response = new KhuyenMaiResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_KHUYEN_MAI_FAIL);
        try {
            if (request != null) {
                if (StringUtils.isNotEmpty(request.getFromDate())
                        && StringUtils.isNotEmpty(request.getToDate())
                        && request.getRate() > 0) {
                    double rate = request.getRate();
                    String fromDate = request.getFromDate();
                    String toDate = request.getToDate();
                    KhuyenMai khuyenMai = khuyenMaiRepository.findKhuyenMaiByRateAndFromDateAndToDateAndCuaHangId(rate, fromDate, toDate, request.getCuaHangId());
                    if (khuyenMai != null) {
                        response.setMessage(CommonConstants.KHUYEN_MAI_IS_EXISTED);
                    } else {
                        KhuyenMai result = new KhuyenMai();
                        //Find max KhuyenMaiId value
                        KhuyenMai temp = khuyenMaiRepository.findTopByOrderByKhuyenMaiIdDesc();
                        if (temp != null) {
                            int khuyenMaiIdMaxValue = Integer.parseInt(temp.getKhuyenMaiId());
                            result.setKhuyenMaiId(Integer.toString(khuyenMaiIdMaxValue + 1));
                        } else {
                            //init first record in DB
                            result.setKhuyenMaiId(NUMBER_ONE);
                        }
                        result.setFromDate(fromDate);
                        result.setToDate(toDate);
                        result.setRate(rate);
                        result.setCuaHangId(request.getCuaHangId());
                        result.setCreatedDate(Instant.now().toString());
                        khuyenMaiRepository.save(result);
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new KhuyenMaiDTO(result));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when createNewKhuyenMai: ", e);
        }
        return response;
    }

    public KhuyenMaiResponse getAllKhuyenMai(String cuaHangId) {
        KhuyenMaiResponse response = new KhuyenMaiResponse();
        response.setMessage(CommonConstants.GET_ALL_KHUYEN_MAI_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            List<KhuyenMai> listKhuyenMai = khuyenMaiRepository.findAllByCuaHangId(cuaHangId);
            if (CollectionUtils.isNotEmpty(listKhuyenMai)) {
                List<KhuyenMaiDTO> khuyenMaiDTOList = new ArrayList<KhuyenMaiDTO>();
                for (KhuyenMai temp : listKhuyenMai) {
                    khuyenMaiDTOList.add(new KhuyenMaiDTO(temp));
                }
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(khuyenMaiDTOList);
                response.setTotalResultCount((long) khuyenMaiDTOList.size());
            }
        } catch (Exception e) {
            log.error("Error when getAllKhuyenMai: ", e);
        }
        return response;
    }

    public KhuyenMaiResponse getKhuyenMaiByKhuyenMaiId(String cuaHangId,String id) {
        KhuyenMaiResponse response = new KhuyenMaiResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_KHUYEN_MAI_BY_KHUYEN_MAI_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(id)) {
                KhuyenMai khuyenMai = khuyenMaiRepository.findKhuyenMaiByKhuyenMaiIdAndCuaHangId(id,cuaHangId);
                if (khuyenMai != null) {
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                    response.setObject(new KhuyenMaiDTO(khuyenMai));
                }
            }
        } catch (Exception e) {
            log.error("Error when getKhuyenMaiByKhuyenMaiId: ", e);
        }
        return response;
    }

    public KhuyenMaiResponse deleteKhuyenMaiByKhuyenMaiId(String cuaHangId,String id) {
        KhuyenMaiResponse response = new KhuyenMaiResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_KHUYEN_MAI_BY_KHUYEN_MAI_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(id)) {
                KhuyenMai khuyenMai = khuyenMaiRepository.findKhuyenMaiByKhuyenMaiIdAndCuaHangId(id, cuaHangId);
                if (khuyenMai != null) {
                    khuyenMaiRepository.delete(khuyenMai);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteKhuyenMaiByKhuyenMaiId: ", e);
        }
        return response;
    }

    public KhuyenMaiResponse updateKhuyenMai(KhuyenMaiUpdateRequest request) {
        KhuyenMaiResponse response = new KhuyenMaiResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_KHUYEN_MAI_FAIL);
        try {
            if (StringUtils.isNotEmpty(request.getKhuyenMaiId())) {
                KhuyenMai khuyenMai = khuyenMaiRepository.findKhuyenMaiByKhuyenMaiIdAndCuaHangId(request.getKhuyenMaiId(), request.getCuaHangId());
                if (khuyenMai == null) {
                    response.setMessage(CommonConstants.GET_KHUYEN_MAI_BY_KHUYEN_MAI_ID_FAIL);
                } else {
                    boolean dateValidated = compareDateForUpdatingKhuyenMai(request.getFromDate(), request.getToDate());
                    if (dateValidated) {
                        khuyenMai.setRate(request.getRate());
                        khuyenMai.setToDate(request.getToDate());
                        khuyenMai.setFromDate(request.getFromDate());
                        khuyenMai.setLastModifiedDate(Instant.now().toString());
                        khuyenMaiRepository.save(khuyenMai);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setSuccess(Boolean.TRUE);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when updateKhuyenMai: ", e);
        }
        return response;
    }

    private boolean compareDateForUpdatingKhuyenMai(String fromDate, String toDate) throws ParseException {
        boolean isValidated = false;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
        Date d1 = sdf.parse(fromDate);
        Date d2 = sdf.parse(toDate);
        if (d1.compareTo(d2) < 0) {
            isValidated = true;
        }
        return isValidated;
    }
}
