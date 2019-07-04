package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.DanhMucDTO;
import com.ocha.boc.entity.DanhMuc;
import com.ocha.boc.repository.DanhMucRepository;
import com.ocha.boc.request.DanhMucRequest;
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

    @Autowired
    private DanhMucRepository danhMucRepository;


    public DanhMucResponse createNewDanhMuc(DanhMucRequest request) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            if (request != null) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByName(request.getName());
                if (danhMuc == null) {
                    danhMuc = new DanhMuc();
                    danhMuc.setAbbreviations(request.getAbbreviations());
                    danhMuc.setName(request.getName());
                    danhMuc.setCreatedDate(Instant.now().toString());
                    danhMucRepository.save(danhMuc);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new DanhMucDTO(danhMuc));
                }
            }
        } catch (Exception e) {
            log.error("Error when createNewDanhMuc: {}", e);
        }
        return response;
    }

    public DanhMucResponse updateDanhMuc(DanhMucRequest request) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            if (request != null) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByName(request.getName());
                if (danhMuc != null) {
                    if (StringUtils.isNotEmpty(request.getAbbreviations())) {
                        danhMuc.setAbbreviations(request.getAbbreviations());
                    }
                    if (StringUtils.isNotEmpty(request.getName())) {
                        danhMuc.setName(request.getName());
                    }
                    danhMuc.setLastModifiedDate(Instant.now().toString());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new DanhMucDTO(danhMuc));
                }
            }
        } catch (Exception e) {
            log.error("Error when updateDanhMuc: {}", e);
        }
        return response;
    }

    public DanhMucResponse findDanhMucById(String id) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucById(id);
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

    public DanhMucResponse getAllDanhMuc() {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            List<DanhMuc> listDanhMuc = danhMucRepository.findAll();
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

    public AbstractResponse deleteDanhMucById(String id) {
        AbstractResponse response = new AbstractResponse();
        try {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucById(id);
                if (danhMuc != null) {
                    danhMucRepository.delete(danhMuc);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteDanhMucById: {}", e);
        }
        return response;
    }

}
