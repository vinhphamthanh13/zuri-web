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

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private OrderRepository orderRepository;


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

    public DanhMucResponse findDanhMucByDanhMucId(String id) {
        DanhMucResponse response = new DanhMucResponse();
        try {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByDanhMucId(id);
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

    public AbstractResponse deleteDanhMucByDanhMucId(String id) {
        AbstractResponse response = new AbstractResponse();
        try {
            response.setMessage(CommonConstants.STR_FAIL_STATUS);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                DanhMuc danhMuc = danhMucRepository.findDanhMucByDanhMucId(id);
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

    public DoanhThuDanhMucResponse getDoanhThuTheoDanhMucByDate(String date) {
        DoanhThuDanhMucResponse response = null;
        try {
            response = buildDoanhThuDanhMucResponse(date);
        } catch (Exception e) {
            log.error("Error when getDoanhThuTheoDanhMucByDate: {}", e);
        }
        return response;
    }

    private DoanhThuDanhMucResponse buildDoanhThuDanhMucResponse(String date) {
        DoanhThuDanhMucResponse response = new DoanhThuDanhMucResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        List<Order> orders = orderRepository.findListOrderByCreateDate(date);
        List<DoanhThuDanhMuc> doanhThuDanhMucList = buildListDoanhThuTheoDanhMuc(orders);
        if (CollectionUtils.isNotEmpty(doanhThuDanhMucList)) {
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObjects(doanhThuDanhMucList);
            response.setTotalResultCount((long) doanhThuDanhMucList.size());
        }
        return response;
    }

    private List<DoanhThuDanhMuc> buildListDoanhThuTheoDanhMuc(List<Order> orders) {
        List<DoanhThuDanhMuc> listDoanhThu = new ArrayList<DoanhThuDanhMuc>();
        if (CollectionUtils.isNotEmpty(orders)) {
            for (Order orderTemp : orders) {
                List<MatHangTieuThu> matHangTieuThuList = orderTemp.getListMatHangTieuThu();
                for (MatHangTieuThu matHangTieuThu : matHangTieuThuList) {
                    DoanhThuDanhMuc doanhThu = createDoanhThuDanhMuc(matHangTieuThu);
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

    private DoanhThuDanhMuc createDoanhThuDanhMuc(MatHangTieuThu matHangTieuThu) {
        DoanhThuDanhMuc doanhThu = new DoanhThuDanhMuc();
        doanhThu.setAmountOfConsumption(matHangTieuThu.getAmountOfConsumption());
        doanhThu.setUnitPrice(matHangTieuThu.getUnitPrice());
        DanhMuc danhMuc = getDanhMucByDanhMucId(matHangTieuThu.getMatHang().getDanhMucId());
        doanhThu.setDanhMuc(danhMuc);
        return doanhThu;
    }

    private DanhMuc getDanhMucByDanhMucId(String danhMucId) {
        DanhMuc danhMuc = new DanhMuc();
        danhMuc = danhMucRepository.findDanhMucByDanhMucId(danhMucId);
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
