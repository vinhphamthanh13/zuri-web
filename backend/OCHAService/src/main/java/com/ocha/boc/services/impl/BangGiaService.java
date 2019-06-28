package com.ocha.boc.services.impl;

import com.ocha.boc.dto.BangGiaDTO;
import com.ocha.boc.entity.BangGia;
import com.ocha.boc.repository.BangGiaRepository;
import com.ocha.boc.request.BangGiaRequest;
import com.ocha.boc.request.BangGiaUpdateRequest;
import com.ocha.boc.response.BangGiaResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BangGiaService {

    @Autowired
    private BangGiaRepository bangGiaRepository;

    public BangGiaResponse createNewBangGia(BangGiaRequest request) {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            BangGia bangGia = new BangGia();
            bangGia.setNumberOfPrice(request.getNumberOfPrice());
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObject(new BangGiaDTO(bangGia));
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
                BangGia bangGia = bangGiaRepository.findBangGiaById(request.getId());
                if (bangGia != null) {
                    bangGia.setNumberOfPrice(request.getNumberOfPrice());
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                    response.setObject(new BangGiaDTO(bangGia));
                }
            }
        } catch (Exception e) {
            log.error("Error when updateBangGiaInformation: {}", e);
        }
        return response;
    }

    public BangGiaResponse findBangGiaById(String id) {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if(StringUtils.isNotEmpty(id)){
                BangGia bangGia = bangGiaRepository.findBangGiaById(id);
                if(bangGia != null){
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
        try{
            List<BangGia> listBangGia = bangGiaRepository.findAll();
            if(listBangGia.size() > 0){
                List<BangGiaDTO> bangGiaDTOList = new ArrayList<>();
                for(BangGia bangGia: listBangGia){
                    BangGiaDTO bangGiaDTO = new BangGiaDTO(bangGia);
                    bangGiaDTOList.add(bangGiaDTO);
                }
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(bangGiaDTOList);
                response.setTotalResultCount((long) bangGiaDTOList.size());
            }
        }catch(Exception e){
            log.error("Error when getAllBangGia: {}", e);
        }
        return response;
    }

    public BangGiaResponse deleteBangGiaById(String id) {
        BangGiaResponse response = new BangGiaResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            if(StringUtils.isNotEmpty(id)){
                BangGia bangGia = bangGiaRepository.findBangGiaById(id);
                if(bangGia != null){
                    bangGiaRepository.delete(bangGia);
                    //TODO delete data on table "BangGiaDetail", reference key bangGiaId
                }
            }
        }catch(Exception e){
            log.error("Error when deleteBangGiaById: {}", e);
        }
        return response;
    }
}
