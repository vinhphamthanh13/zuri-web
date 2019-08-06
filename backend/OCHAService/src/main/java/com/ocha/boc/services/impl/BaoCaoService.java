package com.ocha.boc.services.impl;

import com.ocha.boc.entity.Order;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.response.DoanhThuTongQuanResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class BaoCaoService {

    @Autowired
    private OrderRepository orderRepository;

    public DoanhThuTongQuanResponse getDoanhThuTongQuan(String cuaHangId){
        DoanhThuTongQuanResponse response = new DoanhThuTongQuanResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_DOANH_THU_TONG_QUAN_FAIL);
        try{
            if(StringUtils.isNotEmpty(cuaHangId)){
                String currentDate = DateUtils.getCurrentDate();
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                if(CollectionUtils.isNotEmpty(orders)){
                    response.setNumberOfTransactions(orders.size());
                    BigDecimal total = BigDecimal.ZERO;
                    BigDecimal discount = BigDecimal.ZERO;
                    BigDecimal refunds = BigDecimal.ZERO;
                    BigDecimal serviceFee = BigDecimal.ZERO;
                    BigDecimal tax = BigDecimal.ZERO;
                    for(Order order: orders){
                        total = total.add(order.getTotalMoney());
                        if(order.getDiscountMoney() != null){
                            discount = discount.add(order.getDiscountMoney());
                        }
                        if(order.getRefunds() != null){
                            refunds = refunds.add(order.getRefunds());
                        }
                    }
                    response.setCuaHangId(cuaHangId);
                    response.setRefunds(refunds);
                    response.setTax(tax);
                    response.setTotal(total);
                    response.setServiceFee(serviceFee);
                    response.setDiscount(discount);
                    response.setAverageOfTransactionFee(total.divide(BigDecimal.valueOf(orders.size())));
                }
            }
        }catch (Exception e){
            log.error("Error when getDoanhThuTongQuan: {}", e);
        }
        return response;
    }
}
