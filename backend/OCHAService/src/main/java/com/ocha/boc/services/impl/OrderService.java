package com.ocha.boc.services.impl;

import com.ocha.boc.dto.OrderDTO;
import com.ocha.boc.entity.GiamGia;
import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.entity.Order;
import com.ocha.boc.enums.GiamGiaType;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.enums.OrderType;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.OrderCheckoutObjectRequest;
import com.ocha.boc.request.OrderRejectObjectRequest;
import com.ocha.boc.request.OrderRequest;
import com.ocha.boc.request.OrderUpdateRequest;
import com.ocha.boc.response.OrderResponse;
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
import java.util.Random;

@Service
@Slf4j
public class OrderService {

    private static Random rand = new Random();
    @Autowired
    private OrderRepository orderRepository;

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
                    if (StringUtils.isNotEmpty(request.getOrderLocation())) {
                        order.setOrderLocation(request.getOrderLocation());
                    }
                } else if (request.getOrderType().label.equalsIgnoreCase(OrderType.MANG_ĐI.label)) {
                    String takeAwayCode = generateTakeAWayCode(request.getCuaHangId());
                    order.setTakeAWayOptionCode(takeAwayCode);
                }
                BigDecimal tempTotalMoney = calculateTotalMoney(request.getListMatHangTieuThu());
                order.setTotalMoney(tempTotalMoney);
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
                Order order = orderRepository.findOrderByIdAndCuaHangId(request.getOrderId(), request.getCuaHangId());
                if (order != null) {
                    order.setLastModifiedDate(Instant.now().toString());
                    if (StringUtils.isNotEmpty(request.getOrderLocation())) {
                        order.setOrderLocation(request.getOrderLocation());
                    }
                    order.setListMatHangTieuThu(request.getListMatHangTieuThu());
                    BigDecimal tempTotalMoney = calculateTotalMoney(request.getListMatHangTieuThu());
                    order.setTotalMoney(tempTotalMoney);
                    orderRepository.save(order);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new OrderDTO(order));
                } else {
                    response.setMessage(CommonConstants.ORDER_NOT_EXISTED);
                }
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
            if (request != null) {
                Order order = orderRepository.findOrderByIdAndCuaHangId(request.getOrderId(), request.getCuaHangId());
                if (order != null) {
                    order.setLastModifiedDate(Instant.now().toString());
                    order.setOrderTimeCheckOut(Instant.now().toString());
                    order.setOrderStatus(OrderStatus.CANCEL);
                    order.setListMatHangTieuThu(request.getListMatHangTieuThu());
                    BigDecimal tempTotalMoney = calculateTotalMoney(request.getListMatHangTieuThu());
                    order.setTotalMoney(tempTotalMoney);
                    order.setRefunds(tempTotalMoney);
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
            log.error("Error when rejectOrder: {}", e);
        }
        return response;
    }

    public OrderResponse checkoutOrder(OrderCheckoutObjectRequest request) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.ORDER_CHECKOUT_FAIL);
        BigDecimal tempTotalMoney = BigDecimal.ZERO;
        try {
            if (request != null) {
                Order order = orderRepository.findOrderByIdAndCuaHangId(request.getOrderId(), request.getCuaHangId());
                if (order != null) {
                    order.setOrderStatus(OrderStatus.SUCCESS);
                    order.setLastModifiedDate(Instant.now().toString());
                    order.setOrderTimeCheckOut(Instant.now().toString());
                    order.setListMatHangTieuThu(request.getListMatHangTieuThu());
                    if (request.getGiamGia() != null) {
                        tempTotalMoney = calculateTotalMoneyWhenOrderWasDiscounted(request.getListMatHangTieuThu(), request.getGiamGia());
                    } else {
                        tempTotalMoney = calculateTotalMoney(request.getListMatHangTieuThu());
                    }
                    order.setTotalMoney(tempTotalMoney);
                    order.setCash(request.getCash());
                    BigDecimal excessMoney = request.getCash().subtract(tempTotalMoney);
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

    public OrderResponse getOrdersByCuaHangId(String cuaHangId) {
        OrderResponse response = new OrderResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ORDERS_BY_CUAHANGID_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                List<Order> temp = orderRepository.findAllOrderByCuaHangId(cuaHangId);
                if (CollectionUtils.isNotEmpty(temp)) {
                    List<OrderDTO> ordersResult = new ArrayList<OrderDTO>();
                    for (Order order : temp) {
                        ordersResult.add(new OrderDTO(order));
                    }
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObjects(ordersResult);
                    response.setTotalResultCount((long) ordersResult.size());
                }
            }
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
            int quantity = matHangTieuThu.getQuantity();
            BigDecimal price = matHangTieuThu.getBangGia().getLoaiGia().getPrice();
            total = total.add(price.multiply(BigDecimal.valueOf((double) quantity)));
        }
        return total;
    }

    private BigDecimal calculateTotalMoneyWhenOrderWasDiscounted(List<MatHangTieuThu> listMatTieuThu, GiamGia giamGia) {
        BigDecimal tempTotal = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        if (giamGia.getGiamGiaType().label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THÔNG_THƯỜNG.label)) {
            tempTotal = calculateTotalMoney(listMatTieuThu);
            total = calculateTotalWithGiamGiaThongThuongType(tempTotal, giamGia);
        } else if (giamGia.getGiamGiaType().label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THEO_DANH_MỤC.label)) {
            total = calculateTotalWithGiamGiaDanhMucType(listMatTieuThu, giamGia);
        }
        return total;
    }

    private BigDecimal calculateTotalWithGiamGiaThongThuongType(BigDecimal amountOfAssumption, GiamGia giamGia) {
        BigDecimal total = BigDecimal.ZERO;
        if (giamGia.getDiscountAmount() != null) {
            total = amountOfAssumption.subtract(giamGia.getDiscountAmount());
        } else if (giamGia.getPercentage() != null) {
            BigDecimal discountMoney = amountOfAssumption.multiply(giamGia.getPercentage().divide(BigDecimal.valueOf(100)));
            total = amountOfAssumption.subtract(discountMoney);
        }
        return total;
    }

    private BigDecimal calculateTotalWithGiamGiaDanhMucType(List<MatHangTieuThu> listMatTieuThu, GiamGia giamGia) {
        String danhMucId = giamGia.getDanhMucId();
        BigDecimal percent = giamGia.getPercentage();
        BigDecimal total = BigDecimal.ZERO;
        for (MatHangTieuThu matHangTieuThu : listMatTieuThu) {
            String danhMucIdInMatHang = matHangTieuThu.getMatHang().getDanhMucId();
            int quantity = matHangTieuThu.getQuantity();
            BigDecimal price = matHangTieuThu.getBangGia().getLoaiGia().getPrice();
            if (danhMucId.equalsIgnoreCase(danhMucIdInMatHang)) {
                BigDecimal temp = price.multiply(BigDecimal.valueOf((double) quantity));
                total = temp.multiply(percent.divide(BigDecimal.valueOf(100)));
            } else {
                total = price.multiply(BigDecimal.valueOf((double) quantity));
            }
        }
        return total;
    }

    private boolean checkReceiptCodeIsExisted(String receiptCode, String cuaHangId) {
        boolean isExisted = false;
        Order order = orderRepository.findOrderByReceiptCodeAndCuaHangId(receiptCode, cuaHangId);
        if (order != null) {
            isExisted = true;
        }
        return isExisted;
    }

    private boolean checkTakeAWayCodeIsExisted(String takeAWayCode, String cuaHangId) {
        boolean isExisted = false;
        Order order = orderRepository.findOrderByTakeAWayOptionCodeAndCuaHangId(takeAWayCode, cuaHangId);
        if (order != null) {
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
