package com.ocha.boc.services.impl;

import com.ocha.boc.dto.MathangDTO;
import com.ocha.boc.entity.MatHang;
import com.ocha.boc.repository.MatHangRepository;
import com.ocha.boc.request.MatHangListRequest;
import com.ocha.boc.request.MatHangRequest;
import com.ocha.boc.request.MatHangUpdateRequest;
import com.ocha.boc.response.MatHangResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class MatHangService {

    @Autowired
    private MatHangRepository matHangRepository;

    public MatHangResponse createNewMatHang(MatHangRequest request) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.CREATE_NEW_MAT_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (request != null) {
                if (StringUtils.isNotEmpty(request.getName())) {
                    if (!checkMatHangExisted(request.getName(), request.getCuaHangId())) {
                        MatHang matHang = new MatHang();
                        matHang.setCuaHangId(request.getCuaHangId());
                        matHang.setName(request.getName());
                        matHang.setDanhMucId(request.getDanhMucId());
                        if (CollectionUtils.isNotEmpty(request.getListBangGia())) {
                            matHang.setListBangGia(request.getListBangGia());
                        }
                        matHang.setCreatedDate(DateUtils.getCurrentDateAndTime());
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new MathangDTO(matHang));
                        matHangRepository.save(matHang);
                    } else {
                        response.setMessage(CommonConstants.MAT_HANG_IS_EXISTED);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when createNewMatHang: {}", e);
        }
        return response;
    }

    @CachePut(value = "mathang", key = "{#request.cuaHangId, #request.Id}")
    public MatHang updateMatHangInfor(MatHangUpdateRequest request) {
        return matHangRepository.findMatHangByIdAndCuaHangId(request.getId(), request.getCuaHangId()).map(matHang -> {
            if(StringUtils.isNotEmpty(request.getName())){
                matHang.setName(request.getName());
            }
            if (CollectionUtils.isNotEmpty(request.getListBangGia())) {
                matHang.setListBangGia(request.getListBangGia());
            }
            if (StringUtils.isNotEmpty(request.getDanhMucId())) {
                matHang.setDanhMucId(request.getDanhMucId());
            }
            matHang.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
            return matHangRepository.save(matHang);
        }).orElse(new MatHang());
    }

    @Cacheable(value = "mathang", key = "{#cuaHangId,#id}")
    public MatHang findMatHangById(String cuaHangId, String id) {
        return matHangRepository.findMatHangByIdAndCuaHangId(id, cuaHangId).orElse(new MatHang());
    }

    public MatHangResponse getAllMatHang(String cuaHangId) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.GET_ALL_MAT_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            List<MatHang> matHangList = matHangRepository.findAllByCuaHangId(cuaHangId);
            if (CollectionUtils.isNotEmpty(matHangList)) {
                List<MathangDTO> mathangDTOList = new ArrayList<>();
                for (MatHang matHang : matHangList) {
                    MathangDTO temp = new MathangDTO(matHang);
                    mathangDTOList.add(temp);
                }
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setTotalResultCount((long) matHangList.size());
                response.setObjects(mathangDTOList);
            }
        } catch (Exception e) {
            log.error("Error when getAllMatHang: {}", e);
        }
        return response;
    }

    public Page<MatHang> search(MatHangListRequest request){
        String[] sortSplit = request.getSort().split(",");
        if(!Objects.isNull(request.getSearch())){
            return matHangRepository.query(request, new org.springframework.data.domain.PageRequest(request.getPage(),
                    request.getSize(),
                    (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                            : Sort.Direction.DESC), sortSplit[0]));
        }
        else{
            return matHangRepository.findAll(
                    new org.springframework.data.domain.PageRequest(request.getPage(),
                            request.getSize(),
                            (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                    : Sort.Direction.DESC), sortSplit[0]));
        }
    }

    @CacheEvict(value = "mathang", key = "{#cuaHangId,#id}")
    public MatHangResponse deleteMatHangById(String cuaHangId, String id) {
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.DELETE_MAT_HANG_BY_MAT_HANG_ID_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                if (matHangRepository.existsByIdAndCuaHangId(id, cuaHangId)) {
                    Optional<MatHang> optMatHang = matHangRepository.findMatHangByIdAndCuaHangId(id, cuaHangId);
                    matHangRepository.delete(optMatHang.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                } else {
                    response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteMatHangById: {}", e);
        }
        return response;
    }

    private boolean checkMatHangExisted(String name, String cuaHangId) {
        return matHangRepository.existsByNameAndCuaHangId(name, cuaHangId);
    }

}
