package com.ocha.boc.services.impl;

import com.ocha.boc.dto.BangGiaDTO;
import com.ocha.boc.entity.BangGia;
import com.ocha.boc.entity.BangGiaDetail;
import com.ocha.boc.repository.BangGiaDetailRepository;
import com.ocha.boc.repository.BangGiaRepository;
import com.ocha.boc.request.BangGiaRequest;
import com.ocha.boc.request.BangGiaUpdateRequest;
import com.ocha.boc.response.BangGiaResponse;
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
public class BangGiaService {

    private static final String NUMBER_ONE = "1";

    @Autowired
    private BangGiaRepository bangGiaRepository;

    @Autowired
    private BangGiaDetailRepository bangGiaDetailRepository;

    public BangGiaResponse createNewBangGia(BangGiaRequest request) {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (request.getNumberOfPrice() > 0) {
                BangGia bangGia = new BangGia();
                BangGia temp = bangGiaRepository.findTopByOrderByBangGiaIdDesc();
                if (temp != null) {
                    int bangGiaMaxId = Integer.parseInt(temp.getBangGiaId());
                    bangGia.setBangGiaId(Integer.toString(bangGiaMaxId + 1));
                } else {
                    bangGia.setBangGiaId(NUMBER_ONE);
                }
                bangGia.setNumberOfPrice(request.getNumberOfPrice());
                bangGia.setCreatedDate(Instant.now().toString());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                bangGiaRepository.save(bangGia);
                response.setObject(new BangGiaDTO(bangGia));
            }
        } catch (Exception e) {
            log.error("Error when createNewBangGia: {}", e);
        }
        return response;
    }

    public BangGiaResponse updateBangGiaInformation(BangGiaUpdateRequest request) {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(request.getId())) {
                BangGia bangGia = bangGiaRepository.findBangGiaByBangGiaId(request.getId());
                if (bangGia != null) {
                    if (request.getNumberOfPrice() > 0) {
                        bangGia.setNumberOfPrice(request.getNumberOfPrice());
                        bangGia.setLastModifiedDate(Instant.now().toString());
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setSuccess(Boolean.TRUE);
                        response.setObject(new BangGiaDTO(bangGia));
                        bangGiaRepository.save(bangGia);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when updateBangGiaInformation: {}", e);
        }
        return response;
    }

    public BangGiaResponse findBangGiaByBangGiaId(String id) {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                BangGia bangGia = bangGiaRepository.findBangGiaByBangGiaId(id);
                if (bangGia != null) {
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                    response.setObject(new BangGiaDTO(bangGia));
                }
            }
        } catch (Exception e) {
            log.error("Error when findBangGiaById: {}", e);
        }
        return response;
    }

    public BangGiaResponse getAllBangGia() {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            List<BangGia> listBangGia = bangGiaRepository.findAll();
            if (listBangGia.size() > 0) {
                List<BangGiaDTO> bangGiaDTOList = new ArrayList<>();
                for (BangGia bangGia : listBangGia) {
                    BangGiaDTO bangGiaDTO = new BangGiaDTO(bangGia);
                    bangGiaDTOList.add(bangGiaDTO);
                }
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(bangGiaDTOList);
                response.setTotalResultCount((long) bangGiaDTOList.size());
            }
        } catch (Exception e) {
            log.error("Error when getAllBangGia: {}", e);
        }
        return response;
    }

    public BangGiaResponse deleteBangGiaByBangGiaId(String id) {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                BangGia bangGia = bangGiaRepository.findBangGiaByBangGiaId(id);
                if (bangGia != null) {
                    bangGiaRepository.delete(bangGia);
                    //Remove all data in Bang Gia Detail based on the Bang Gia Id
                    List<BangGiaDetail> bangGiaDetailList = bangGiaDetailRepository.findBangGiaDetailByBangGiaId(id);
                    for (BangGiaDetail bangGiaDetail : bangGiaDetailList) {
                        bangGiaDetailRepository.delete(bangGiaDetail);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteBangGiaById: {}", e);
        }
        return response;
    }
}
