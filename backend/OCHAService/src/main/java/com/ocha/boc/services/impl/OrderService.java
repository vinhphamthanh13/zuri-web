package com.ocha.boc.services.impl;

import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.entity.Order;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.enums.OrderType;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.OrderRequest;
import com.ocha.boc.response.OrderResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    final int SHORT_ID_LENGTH = 5;

    public OrderResponse initialOrder(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.INITIAL_ORDER_FAIL);
        try {
            if (request != null) {
                Order order = new Order();
                order.setCuaHangId(request.getCuaHangId());
                order.setWaiterName(request.getWaiterName());
                order.setOrderType(request.getOrderType());
                order.setCreatedDate(Instant.now().toString());
                order.setOrderTime(Instant.now().toString());
                order.setOrderStatus(OrderStatus.PENDING);
                order.setListMatHangTieuThu(request.getListMatHangTieuThu());
                if (request.getOrderType().label.equalsIgnoreCase(OrderType.DÙNG_TẠI_BÀN.label)) {
                    if(StringUtils.isNotEmpty(request.getOrderLocation())){
                        order.setOrderLocation(request.getOrderLocation());
                    }
                }else if(request.getOrderType().label.equalsIgnoreCase(OrderType.MANG_ĐI.label)){
                        order.setTakeAWayOptionCode(generateUUIDCode());
                }
                BigDecimal tempTotalMoney = calculateTotalMoney(request.getListMatHangTieuThu());
                order.setTotalMoney(tempTotalMoney);
            }
        } catch (Exception e) {
            log.error("Error when initialOrder: {}", e);
        }
        return response;
    }

    private String generateUUIDCode(){
        String shortId = RandomStringUtils.random(SHORT_ID_LENGTH);
        return shortId;
    }

    private BigDecimal calculateTotalMoney(List<MatHangTieuThu> listMatHangTieuThu){
        BigDecimal total = BigDecimal.ZERO;
        for(MatHangTieuThu matHangTieuThu: listMatHangTieuThu){
            int quantity = matHangTieuThu.getQuantity();
            BigDecimal price = matHangTieuThu.getBangGia().getLoaiGia().getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf((double) quantity)));
        }
        return total;
    }
}
