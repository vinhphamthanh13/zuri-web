package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.DanhMucDTO;
import com.ocha.boc.entity.DanhMuc;
import com.ocha.boc.repository.DanhMucRepository;
import com.ocha.boc.request.DanhMucRequest;
import com.ocha.boc.request.DanhMucUpdateRequest;
import com.ocha.boc.response.DanhMucResponse;
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
public class DanhMucService {

    private static final String NUMBER_ONE = "1";

    @Autowired
    private DanhMucRepository danhMucRepository;



    public DanhMucResponse createNewDanhMuc(DanhMucRequest request) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.CREATE_NEW_DANH_MUC_FAIL);
            response.setSuccess(Boolean.FALSE);
            if (request != null) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByName(request.getName());
                if (danhMuc == null) {
                    danhMuc = new DanhMuc();
                    //Find max DanhMucId Value
                    DanhMuc temp = danhMucRepository.findTopByOrderByDanhMucIdDesc();
                    if (temp != null) {
                        int danhMucIdMaxValue = Integer.parseInt(temp.getDanhMucId());
                        danhMuc.setDanhMucId(Integer.toString(danhMucIdMaxValue + 1));
                    } else {
                        //init first record in DB
                        danhMuc.setDanhMucId(NUMBER_ONE);
                    }
                    danhMuc.setCuaHangId(request.getCuaHangId());
                    danhMuc.setAbbreviations(request.getAbbreviations());
                    danhMuc.setName(request.getName());
                    danhMuc.setCreatedDate(Instant.now().toString());
                    danhMucRepository.save(danhMuc);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new DanhMucDTO(danhMuc));
                }else{
                    response.setMessage(CommonConstants.DANH_MUC_IS_EXISTED);
                }
            }
        } catch (Exception e) {
            log.error("Error when createNewDanhMuc: {}", e);
        }
        return response;
    }

    public DanhMucResponse updateDanhMuc(DanhMucUpdateRequest request) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.UPDATE_DANH_MUC_FAIL);
            response.setSuccess(Boolean.FALSE);
            if (request != null) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByDanhMucIdAndCuaHangId(request.getDanhMucId(), request.getCuaHangId());
                if (danhMuc != null) {
                    if(StringUtils.isNotEmpty(request.getCuaHangId())){
                        danhMuc.setCuaHangId(request.getCuaHangId());
                    }
                    if (StringUtils.isNotEmpty(request.getAbbreviations())) {
                        danhMuc.setAbbreviations(request.getAbbreviations());
                    }
                    if (StringUtils.isNotEmpty(request.getName())) {
                        danhMuc.setName(request.getName());
                    }
                    danhMuc.setLastModifiedDate(Instant.now().toString());
                    danhMucRepository.save(danhMuc);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new DanhMucDTO(danhMuc));
                } else {
                    response.setMessage(CommonConstants.DANH_MUC_NAME_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when updateDanhMuc: {}", e);
        }
        return response;
    }

    public DanhMucResponse findDanhMucByDanhMucId(String id, String cuaHangId) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.DANH_MUC_NAME_IS_NULL);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByDanhMucIdAndCuaHangId(id, cuaHangId);
                if (danhMuc != null) {
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new DanhMucDTO(danhMuc));
                }
            }
        } catch (Exception e) {
            log.error("Error when findDanhMucById: {}", e);
        }
        return response;
    }

    public DanhMucResponse getAllDanhMuc(String cuaHangId) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.GET_ALL_DANH_MUC_FAIL);
            response.setSuccess(Boolean.FALSE);
            List<DanhMuc> listDanhMuc = danhMucRepository.findAllByCuaHangId(cuaHangId);
            if (CollectionUtils.isNotEmpty(listDanhMuc)) {
                List<DanhMucDTO> danhMucDTOList = new ArrayList<>();
                for (DanhMuc danhMuc : listDanhMuc) {
                    DanhMucDTO temp = new DanhMucDTO(danhMuc);
                    danhMucDTOList.add(temp);
                }
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(danhMucDTOList);
                response.setTotalResultCount((long) danhMucDTOList.size());
            }
        } catch (Exception e) {
            log.error("Error when getAllDanhMuc: {}", e);
        }
        return response;
    }

    public AbstractResponse deleteDanhMucByDanhMucId(String id, String cuaHangId) {
        AbstractResponse response = new AbstractResponse();
        try {
            response.setMessage(CommonConstants.DELETE_DANH_MUC_BY_DANH_MUC_ID_FAIL);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByDanhMucIdAndCuaHangId(id, cuaHangId);
                if (danhMuc != null) {
                    danhMucRepository.delete(danhMuc);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                } else {
                    response.setMessage(CommonConstants.DANH_MUC_NAME_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteDanhMucById: {}", e);
        }
        return response;
    }

}
