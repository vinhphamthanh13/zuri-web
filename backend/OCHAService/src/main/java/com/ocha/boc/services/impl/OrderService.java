package com.ocha.boc.services.impl;

import com.ocha.boc.entity.MatHangBanChay;
import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.entity.Order;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.response.MatHangBanChayResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public MatHangBanChayResponse getListMatHangBanChay() {
        MatHangBanChayResponse response = new MatHangBanChayResponse();
        response.setMessage(CommonConstants.STR_FAIL_STATUS);
        response.setSuccess(Boolean.FALSE);
        try {
            String currentDate = DateUtils.getCurrentDate();
            List<Order> orders = orderRepository.findListOrderByCreateDate(currentDate);
            List<MatHangBanChay> matHangBanChayList = buildListMatHangBanChay(orders);
            if (CollectionUtils.isNotEmpty(matHangBanChayList)) {
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(matHangBanChayList);
                response.setTotalResultCount((long) matHangBanChayList.size());
            }
        } catch (Exception e) {
            log.error("Error when getListMatHangBanChay: {}", e);
        }
        return response;
    }

    private List<MatHangBanChay> buildListMatHangBanChay(List<Order> orders) {
        List<MatHangBanChay> matHangBanChayList = new ArrayList<MatHangBanChay>();
        if (CollectionUtils.isNotEmpty(orders)) {
            for (Order orderTemp : orders) {
                List<MatHangTieuThu> matHangTieuThuList = orderTemp.getListMatHangTieuThu();
                for (MatHangTieuThu matHangTieuThu : matHangTieuThuList) {
                    MatHangBanChay matHangBanChay = createMatHangBanChay(matHangTieuThu);
                    boolean isExisted = checkMatHangBanChayExisted(matHangBanChay, matHangBanChayList);
                    if (isExisted) {
                        matHangBanChayList = updateAmountOfConsumption(matHangBanChay, matHangBanChayList);
                    } else {
                        matHangBanChayList.add(matHangBanChay);
                    }
                }
            }
            sortingBanChayList(matHangBanChayList);
            calcualteCostPriceMatHangBanChay(matHangBanChayList);
        }
        return matHangBanChayList;
    }

    private MatHangBanChay createMatHangBanChay(MatHangTieuThu matHangTieuThu) {
        MatHangBanChay matHangBanChay = new MatHangBanChay();
        matHangBanChay.setMatHang(matHangTieuThu.getMatHang());
        matHangBanChay.setAmountOfConsumption(matHangTieuThu.getAmountOfConsumption());
        matHangBanChay.setUnitPrice(matHangTieuThu.getUnitPrice());
        return matHangBanChay;
    }

    private List<MatHangBanChay> updateAmountOfConsumption(MatHangBanChay matHangBanChay, List<MatHangBanChay> banChayList) {
        for (MatHangBanChay temp : banChayList) {
            if (temp.getMatHang().getName().equalsIgnoreCase(matHangBanChay.getMatHang().getName())) {
                temp.setAmountOfConsumption(matHangBanChay.getAmountOfConsumption() + temp.getAmountOfConsumption());
                break;
            }
        }
        return banChayList;
    }

    private boolean checkMatHangBanChayExisted(MatHangBanChay matHangBanChay, List<MatHangBanChay> banChayList) {
        boolean isExisted = false;
        for (MatHangBanChay temp : banChayList) {
            if (temp.getMatHang().getName().equalsIgnoreCase(matHangBanChay.getMatHang().getName()) && temp.getUnitPrice().compareTo(matHangBanChay.getUnitPrice()) == 0) {
                isExisted = true;
                break;
            }
        }
        return isExisted;
    }

    private void sortingBanChayList(List<MatHangBanChay> banChayList){
        Collections.sort(banChayList, new Comparator<MatHangBanChay>() {
            @Override
            public int compare(final MatHangBanChay object1, final MatHangBanChay object2) {
                return object1.getAmountOfConsumption() > object2.getAmountOfConsumption() ? -1 :(object1.getAmountOfConsumption() < object2.getAmountOfConsumption() ? 1 : 0);
            }
        });
    }

    private void calcualteCostPriceMatHangBanChay(List<MatHangBanChay> banChayList){
        for (MatHangBanChay temp: banChayList){
            temp.setCostPrice(temp.getUnitPrice().multiply(BigDecimal.valueOf((long)temp.getAmountOfConsumption())));
        }
    }
}
