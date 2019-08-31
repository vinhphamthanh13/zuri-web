package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.NhanVienDTO;
import com.ocha.boc.entity.NhanVien;
import com.ocha.boc.repository.NhanVienRepository;
import com.ocha.boc.request.NhanVienRequest;
import com.ocha.boc.response.NhanVienResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    public NhanVienResponse createNewNhanVien(NhanVienRequest request) {
        NhanVienResponse response = new NhanVienResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_NHAN_VIEN_FAIL);
        try {
            Optional<NhanVien> optNhanVien = nhanVienRepository.findNhanVienByUsername(request.getUsername());
            if (optNhanVien.isPresent()) {
                response.setMessage(CommonConstants.USERNAME_EXISTED);
            } else {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setCuaHangId(request.getCuaHangId());
                nhanVien.setFullName(request.getFullName());
                nhanVien.setNhanVienType(request.getNhanVienType());
                nhanVien.setPassword(request.getPassword());
                nhanVien.setUsername(request.getUsername());
                nhanVien.setCreatedDate(DateUtils.getCurrentDateAndTime());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new NhanVienDTO(nhanVien));
                nhanVienRepository.save(nhanVien);
            }
        } catch (Exception e) {
            log.error("Error when createNewNhanVien: {}", e);
        }
        return response;
    }

    public NhanVienResponse getListNhanVienByCuaHangId(String cuaHangId) {
        NhanVienResponse response = new NhanVienResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_LIST_NHAN_VIEN_BY_CUA_HANG_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                List<NhanVien> temp = nhanVienRepository.findAllByCuaHangId(cuaHangId);
                if (CollectionUtils.isNotEmpty(temp)) {
                    List<NhanVienDTO> result = new ArrayList<NhanVienDTO>();
                    for (NhanVien nhanVien : temp) {
                        result.add(new NhanVienDTO(nhanVien));
                    }
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObjects(result);
                    response.setTotalResultCount((long) result.size());
                } else {
                    response.setMessage(CommonConstants.NOT_EXISTED_ANY_NHAN_VIEN);
                }

            }
        } catch (Exception e) {
            log.error("Error when getListNhanVienByCuaHangId: {}", e);
        }
        return response;
    }

    public AbstractResponse deleteNhanVienByNhanVienId(String nhanVienId) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_NHAN_VIEN_BY_NHAN_VIEN_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(nhanVienId)) {
                Optional<NhanVien> optNhanVien = nhanVienRepository.findNhanVienById(nhanVienId);
                if (optNhanVien.isPresent()) {
                    nhanVienRepository.delete(optNhanVien.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                } else {
                    response.setMessage(CommonConstants.NHAN_VIEN_IS_NOT_EXISTED);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteNhanVienByNhanVienId: {}", e);
        }
        return response;
    }
}
