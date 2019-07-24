package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.DanhMucDTO;
import com.ocha.boc.entity.DanhMuc;
import com.ocha.boc.entity.DoanhThuDanhMuc;
import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.entity.Order;
import com.ocha.boc.repository.DanhMucRepository;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.DanhMucRequest;
import com.ocha.boc.request.DanhMucUpdateRequest;
import com.ocha.boc.response.DanhMucResponse;
import com.ocha.boc.response.DoanhThuDanhMucResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DanhMucService {

    private static final String NUMBER_ONE = "1";

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private OrderRepository orderRepository;


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

    public DoanhThuDanhMucResponse getDoanhThuTheoDanhMucByDate(String date, String cuaHangId) {
        DoanhThuDanhMucResponse response = null;
        try {
            response = buildDoanhThuDanhMucResponse(date, cuaHangId);
        } catch (Exception e) {
            log.error("Error when getDoanhThuTheoDanhMucByDate: {}", e);
        }
        return response;
    }

    private DoanhThuDanhMucResponse buildDoanhThuDanhMucResponse(String date, String cuaHangId) {
        DoanhThuDanhMucResponse response = new DoanhThuDanhMucResponse();
        response.setMessage(CommonConstants.GET_DOANH_THU_THEO_DANH_MUC_FAIL);
        response.setSuccess(Boolean.FALSE);
        List<Order> orders = orderRepository.findListOrderByCreateDateAndCuaHangId(date, cuaHangId);
        List<DoanhThuDanhMuc> doanhThuDanhMucList = buildListDoanhThuTheoDanhMuc(orders , cuaHangId);
        if (CollectionUtils.isNotEmpty(doanhThuDanhMucList)) {
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObjects(doanhThuDanhMucList);
            response.setTotalResultCount((long) doanhThuDanhMucList.size());
        }
        return response;
    }

    private List<DoanhThuDanhMuc> buildListDoanhThuTheoDanhMuc(List<Order> orders, String cuaHangId) {
        List<DoanhThuDanhMuc> listDoanhThu = new ArrayList<DoanhThuDanhMuc>();
        if (CollectionUtils.isNotEmpty(orders)) {
            for (Order orderTemp : orders) {
                List<MatHangTieuThu> matHangTieuThuList = orderTemp.getListMatHangTieuThu();
                for (MatHangTieuThu matHangTieuThu : matHangTieuThuList) {
                    DoanhThuDanhMuc doanhThu = createDoanhThuDanhMuc(matHangTieuThu, cuaHangId);
                    boolean isExist = checkDoanhThuDanhMucExist(doanhThu, listDoanhThu);
                    if (isExist) {
                        listDoanhThu = updateAmountOfConsumptionAndCalculateCostPrice(doanhThu, listDoanhThu);
                    } else {
                        double costPriceTemp = doanhThu.getUnitPrice().multiply(BigDecimal.valueOf((long) doanhThu.getAmountOfConsumption())).doubleValue();
                        doanhThu.setCostPrice(BigDecimal.valueOf(costPriceTemp));
                        listDoanhThu.add(doanhThu);
                    }
                }
            }
        }
        return listDoanhThu;
    }

    private DoanhThuDanhMuc createDoanhThuDanhMuc(MatHangTieuThu matHangTieuThu, String cuaHangId) {
        DoanhThuDanhMuc doanhThu = new DoanhThuDanhMuc();
        doanhThu.setAmountOfConsumption(matHangTieuThu.getAmountOfConsumption());
        doanhThu.setUnitPrice(matHangTieuThu.getUnitPrice());
        DanhMuc danhMuc = getDanhMucByDanhMucId(matHangTieuThu.getMatHang().getDanhMucId(), cuaHangId);
        doanhThu.setDanhMuc(danhMuc);
        return doanhThu;
    }

    private DanhMuc getDanhMucByDanhMucId(String danhMucId, String cuaHangId) {
        DanhMuc danhMuc = new DanhMuc();
        danhMuc = danhMucRepository.findDanhMucByDanhMucIdAndCuaHangId(danhMucId, cuaHangId);
        return danhMuc;
    }

    private boolean checkDoanhThuDanhMucExist(DoanhThuDanhMuc doanhThu, List<DoanhThuDanhMuc> listDoanhThu) {
        boolean isExist = false;
        for (DoanhThuDanhMuc temp : listDoanhThu) {
            if (temp.getDanhMuc().getName().equalsIgnoreCase(doanhThu.getDanhMuc().getName())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    private List<DoanhThuDanhMuc> updateAmountOfConsumptionAndCalculateCostPrice(DoanhThuDanhMuc doanhThu, List<DoanhThuDanhMuc> listDoanhThu) {
        for (DoanhThuDanhMuc temp : listDoanhThu) {
            if (temp.getDanhMuc().getName().equalsIgnoreCase(doanhThu.getDanhMuc().getName())) {
                temp.setAmountOfConsumption(temp.getAmountOfConsumption() + doanhThu.getAmountOfConsumption());
                double costPriceTemp = doanhThu.getUnitPrice().multiply(BigDecimal.valueOf((long) doanhThu.getAmountOfConsumption())).doubleValue();
                double costPrice = temp.getCostPrice().doubleValue() + costPriceTemp;
                temp.setCostPrice(BigDecimal.valueOf(costPrice));
                break;
            }
        }
        return listDoanhThu;
    }

}
