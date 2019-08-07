package com.ocha.boc.services.impl;

import com.ocha.boc.entity.Order;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.DoanhThuTongQuanRequest;
import com.ocha.boc.response.DoanhThuTongQuanResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
public class BaoCaoService {

    @Autowired
    private OrderRepository orderRepository;

    public DoanhThuTongQuanResponse getDoanhThuTongQuan(String cuaHangId) {
        DoanhThuTongQuanResponse response = new DoanhThuTongQuanResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_DOANH_THU_TONG_QUAN_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String currentDate = DateUtils.getCurrentDate();
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(cuaHangId);
                    analysisDoanhThuTongQuan(orders, response);
                }
            }
        } catch (Exception e) {
            log.error("Error when getDoanhThuTongQuan: {}", e);
        }
        return response;
    }

    public DoanhThuTongQuanResponse getDoanhThuTongQuanInRangeDate(DoanhThuTongQuanRequest request) {
        DoanhThuTongQuanResponse response = new DoanhThuTongQuanResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_DOANH_THU_TONG_QUAN_IN_RANGE_DATE_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getCuaHangId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(request.getCuaHangId());
                    analysisDoanhThuTongQuan(orders, response);
                }
            }
        } catch (Exception e) {
            log.error("Error when getDoanhThuTongQuanInRangeDate: {}", e);
        }
        return response;
    }

    private void analysisDoanhThuTongQuan(List<Order> orders, DoanhThuTongQuanResponse response) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;
        BigDecimal refunds = BigDecimal.ZERO;
        BigDecimal serviceFee = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        for (Order order : orders) {
            total = total.add(order.getTotalMoney());
            if (order.getDiscountMoney() != null) {
                discount = discount.add(order.getDiscountMoney());
            }
            if (order.getRefunds() != null) {
                refunds = refunds.add(order.getRefunds());
            }
        }
        response.setNumberOfTransactions(orders.size());
        response.setRefunds(refunds);
        response.setTax(tax);
        response.setTotal(total);
        response.setServiceFee(serviceFee);
        response.setDiscount(discount);
        response.setAverageOfTransactionFee(total.divide(BigDecimal.valueOf(orders.size()), 0, RoundingMode.HALF_UP));
        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
        response.setSuccess(Boolean.TRUE);
    }


}
