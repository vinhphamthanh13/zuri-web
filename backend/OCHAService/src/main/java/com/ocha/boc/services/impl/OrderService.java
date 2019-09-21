package com.ocha.boc.services.impl;

import com.ocha.boc.dto.OrderDTO;
import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.entity.Order;
import com.ocha.boc.enums.DiscountType;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.enums.OrderType;
import com.ocha.boc.error.ResourceNotFoundException;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.*;
import com.ocha.boc.response.OrderResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class OrderService {

    private static final String TOTAL_MONEY = "TOTAL";
    private static final String DISCOUNT_MONEY = "DISCOUNT";
    private static final String RESOURCE_NAME = "Order";
    private static Random rand = new Random();
    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse initialOrder(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.INITIAL_ORDER_FAIL);
        try {
            if (!Objects.isNull(request)) {
                Order order = new Order();
                order.setCuaHangId(request.getCuaHangId());
                order.setWaiterName(request.getWaiterName());
                order.setOrderType(request.getOrderType());
                order.setCreatedDate(DateUtils.getCurrentDate());
                order.setOrderTime(Instant.now().toString());
                order.setOrderStatus(OrderStatus.PENDING);
                order.setListMatHangTieuThu(request.getListMatHangTieuThu());
                if (request.getOrderType().label.equalsIgnoreCase(OrderType.DÙNG_TẠI_BÀN.label)) {
                    if (StringUtils.isNotEmpty(request.getOrderLocation())) {
                        order.setOrderLocation(request.getOrderLocation());
                    }
                } else {
                    order.setTakeAWayOptionCode(generateTakeAWayCode(request.getCuaHangId()));
                }
                order.setTotalMoney(calculateTotalMoney(request.getListMatHangTieuThu()));
                orderRepository.save(order);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new OrderDTO(order));
            }
        } catch (Exception e) {
            log.error("Error when initialOrder: {}", e);
        }
        return response;
    }


    public OrderResponse updateOrderInformation(OrderUpdateRequest request) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_ORDER_FAIL);
        try {
            if (request != null) {
                Optional<Order> order = Optional.ofNullable(orderRepository.findOrderByIdAndCuaHangId(request.getOrderId(),
                        request.getCuaHangId()).map(orderDb -> {
                    if (StringUtils.isNotEmpty(request.getOrderLocation())) {
                        orderDb.setOrderLocation(request.getOrderLocation());
                    }
                    if (CollectionUtils.isNotEmpty(request.getListMatHangTieuThu())) {
                        orderDb.setListMatHangTieuThu(request.getListMatHangTieuThu());
                        orderDb.setTotalMoney(calculateTotalMoney(request.getListMatHangTieuThu()));
                    }
                    orderDb.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                    return orderRepository.save(orderDb);
                }).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, CommonConstants.ORDER_NOT_EXISTED, request)));
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new OrderDTO(order.get()));
            }
        } catch (Exception e) {
            log.error("Error when updateOrderInformation: {}", e);
        }
        return response;
    }

    public OrderResponse rejectOrder(OrderRejectObjectRequest request) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.ORDER_CHECKOUT_FAIL);
        try {
            if (!Objects.isNull(request)) {
                Optional<Order> order = orderRepository.findOrderByIdAndCuaHangId(request.getOrderId(), request.getCuaHangId());
                if (order.isPresent()) {
                    order.get().setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                    order.get().setOrderTimeCheckOut(Instant.now().toString());
                    BigDecimal totalMoney = calculateTotalMoney(request.getListMatHangTieuThu());
                    order.get().setTotalMoney(totalMoney);
                    order.get().setRefunds(totalMoney);
                    order.get().setReceiptCode(generateReceiptCode(request.getCuaHangId()));
                    orderRepository.save(order.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new OrderDTO(order.get()));
                } else {
                    response.setMessage(CommonConstants.ORDER_NOT_EXISTED);
                }
            }
        } catch (Exception e) {
            log.error("Error when rejectOrder: {}", e);
        }
        return response;
    }


    public OrderResponse checkoutOrder(OrderCheckoutObjectRequest request) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.ORDER_CHECKOUT_FAIL);
        BigDecimal amountOfAssumption = BigDecimal.ZERO;
        try {
            if (!Objects.isNull(request)) {
                Optional<Order> optOrder = orderRepository.findOrderByIdAndCuaHangId(request.getOrderId(), request.getCuaHangId());
                if (optOrder.isPresent()) {
                    Order order = optOrder.get();
                    order.setOrderStatus(OrderStatus.SUCCESS);
                    order.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                    order.setOrderTimeCheckOut(Instant.now().toString());
                    order.setListMatHangTieuThu(request.getListMatHangTieuThu());
                    if (!request.getDiscountType().equals(DiscountType.NONE)) {
                        Map<String, BigDecimal> typeOrPrice = new HashMap<String, BigDecimal>();
                        if (request.getDiscountType().equals(DiscountType.GIẢM_GIÁ_THEO_DANH_MỤC)) {
                            typeOrPrice = calculateTotalWithGiamGiaDanhMucType(request.getListMatHangTieuThu(), request.getDanhMucIsDiscountedId(), request.getGiamGiaPercentage());
                            order.setGiamGiaPercentage(request.getGiamGiaPercentage());
                        } else if (request.getDiscountType().equals(DiscountType.GIẢM_GIÁ_THÔNG_THƯỜNG)) {
                            if (request.getGiamGiaPercentage() != null) {
                                typeOrPrice = calculateTotalWithGiamGiaThongThuongTypeWithPercentage(calculateTotalMoney(request.getListMatHangTieuThu()),
                                        request.getGiamGiaPercentage());
                                order.setGiamGiaPercentage(request.getGiamGiaPercentage());
                            } else if (request.getGiamGiaDiscountAmount() != null) {
                                typeOrPrice = calculateTotalWithGiamGiaThongThuongTypeWithDiscountAmount(calculateTotalMoney(request.getListMatHangTieuThu()),
                                        request.getGiamGiaDiscountAmount());
                                order.setGiamGiaDiscountAmount(request.getGiamGiaDiscountAmount());
                            }
                        }
                        order.setTotalMoney(typeOrPrice.get(TOTAL_MONEY));
                        order.setDiscountMoney(typeOrPrice.get(DISCOUNT_MONEY));
                        order.setGiamGiaName(request.getGiamGiaName());
                        order.setDiscountType(request.getDiscountType());
                    } else {
                        amountOfAssumption = calculateTotalMoney(request.getListMatHangTieuThu());
                        order.setTotalMoney(amountOfAssumption);
                    }
                    order.setCash(request.getCash());
                    BigDecimal excessMoney = request.getCash().subtract(amountOfAssumption);
                    if (excessMoney.compareTo(BigDecimal.ZERO) != 0) {
                        order.setExcessCash(excessMoney);
                    }
                    if (request.getTips() != null) {
                        order.setTips(request.getTips());
                    }
                    String receiptCode = generateReceiptCode(request.getCuaHangId());
                    order.setReceiptCode(receiptCode);
                    orderRepository.save(order);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new OrderDTO(order));
                } else {
                    response.setMessage(CommonConstants.ORDER_NOT_EXISTED);
                }
            }
        } catch (Exception e) {
            log.error("Error when checkoutOrder: {}", e);
        }
        return response;
    }

    public OrderResponse getOrdersByCuaHangId(PageRequest pageRequest, String cuaHangId) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ORDERS_BY_CUAHANGID_FAIL);
        String[] sortSplit = pageRequest.getSort().split(",");
        try {
            Page<Order> orders = orderRepository.findAllByCuaHangId(new org.springframework.data.domain.PageRequest(pageRequest.getPage(), pageRequest.getSize(),
                    (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC)), cuaHangId);
            List<OrderDTO> ordersResult = new ArrayList<OrderDTO>();
            for (Order order : orders) {
                ordersResult.add(new OrderDTO(order));
            }
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObjects(ordersResult);
            response.setTotalResultCount((long) ordersResult.size());
        } catch (Exception e) {
            log.error("Error when getOrdersByCuaHangId: {}", e);
        }
        return response;
    }

    private String generateRandomNumberCode() {
        int numberRandom = rand.nextInt();
        if (numberRandom < 0) {
            numberRandom = -numberRandom;
        }
        return String.valueOf(numberRandom);
    }

    private BigDecimal calculateTotalMoney(List<MatHangTieuThu> listMatHangTieuThu) {
        BigDecimal total = BigDecimal.ZERO;
        for (MatHangTieuThu matHangTieuThu : listMatHangTieuThu) {
            total = total.add(matHangTieuThu.getUnitPrice().multiply(BigDecimal.valueOf((double) matHangTieuThu.getQuantity())));
        }
        return total;
    }

    private Map<String, BigDecimal> calculateTotalWithGiamGiaThongThuongTypeWithDiscountAmount(BigDecimal amountOfAssumption, BigDecimal discountAmount) {
        Map<String, BigDecimal> results = new HashMap<String, BigDecimal>();
        BigDecimal total = amountOfAssumption.subtract(discountAmount);
        results.put(TOTAL_MONEY, total);
        results.put(DISCOUNT_MONEY, BigDecimal.ZERO);
        return results;
    }

    private Map<String, BigDecimal> calculateTotalWithGiamGiaThongThuongTypeWithPercentage(BigDecimal amountOfAssumption, BigDecimal percentage) {
        Map<String, BigDecimal> results = new HashMap<String, BigDecimal>();
        BigDecimal discountMoney = amountOfAssumption.multiply(percentage.divide(BigDecimal.valueOf(100)));
        BigDecimal total = amountOfAssumption.subtract(discountMoney);
        results.put(TOTAL_MONEY, total);
        results.put(DISCOUNT_MONEY, discountMoney);
        return results;
    }

    private Map<String, BigDecimal> calculateTotalWithGiamGiaDanhMucType(List<MatHangTieuThu> listMatTieuThu, String danhMucIsDiscountedId, BigDecimal percentage) {
        Map<String, BigDecimal> results = new HashMap<String, BigDecimal>();
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal discountMoney = BigDecimal.ZERO;
        for (MatHangTieuThu matHangTieuThu : listMatTieuThu) {
            if (danhMucIsDiscountedId.equalsIgnoreCase(matHangTieuThu.getDanhMucId())) {
                //origin price * quantity
                BigDecimal temp = matHangTieuThu.getUnitPrice().multiply(BigDecimal.valueOf((double) matHangTieuThu.getQuantity()));
                //calculate discount money: discount money = discount + (temp * percent discount)
                discountMoney = discountMoney.add(temp.multiply(percentage.divide(BigDecimal.valueOf(100))));
                //amount of money have to pay = temp - discount money
                BigDecimal amountOfAssumption = temp.subtract(discountMoney);
                total = total.add(amountOfAssumption);
            } else {
                total = total.add(matHangTieuThu.getUnitPrice().multiply(BigDecimal.valueOf((double) matHangTieuThu.getQuantity())));
            }
        }
        results.put(TOTAL_MONEY, total);
        results.put(DISCOUNT_MONEY, discountMoney);
        return results;
    }

    private boolean checkReceiptCodeIsExisted(String receiptCode, String cuaHangId) {
        boolean isExisted = false;
        Optional<Order> order = orderRepository.findOrderByReceiptCodeAndCuaHangId(receiptCode, cuaHangId);
        if (order.isPresent()) {
            isExisted = true;
        }
        return isExisted;
    }

    private boolean checkTakeAWayCodeIsExisted(String takeAWayCode, String cuaHangId) {
        boolean isExisted = false;
        Optional<Order> order = orderRepository.findOrderByTakeAWayOptionCodeAndCuaHangId(takeAWayCode, cuaHangId);
        if (order.isPresent()) {
            isExisted = true;
        }
        return isExisted;
    }

    private String generateTakeAWayCode(String cuaHangId) {
        boolean isExisted = true;
        String takeAwayCode = "T" + generateRandomNumberCode();
        while (!isExisted) {
            isExisted = checkTakeAWayCodeIsExisted(takeAwayCode, cuaHangId);
        }
        return takeAwayCode;
    }

    private String generateReceiptCode(String cuaHangId) {
        boolean isExisted = true;
        String receiptCode = generateRandomNumberCode();
        while (!isExisted) {
            isExisted = checkReceiptCodeIsExisted(receiptCode, cuaHangId);
        }
        return receiptCode;
    }
}
