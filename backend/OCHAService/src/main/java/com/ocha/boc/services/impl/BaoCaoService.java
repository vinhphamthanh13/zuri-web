package com.ocha.boc.services.impl;

import com.ocha.boc.entity.*;
import com.ocha.boc.enums.GiamGiaType;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.enums.RevenuePercentageStatusType;
import com.ocha.boc.repository.CategoryRepository;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.AbstractBaoCaoRequest;
import com.ocha.boc.response.*;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.IntStream;

@Service
@Slf4j
public class BaoCaoService {

    private static final String TOTAL_PRICE = "TOTAL";
    private static final String QUANTITY = "QUANTITY";
    private static final int ZERO_NUMBER = 0;
    private static final String ONE_HUNDRED_PERCENT = "100%";

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public DoanhThuTongQuanResponse getDoanhThuTongQuan(String cuaHangId) {
        DoanhThuTongQuanResponse response = new DoanhThuTongQuanResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_DOANH_THU_TONG_QUAN_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndCuaHangId(DateUtils.getCurrentDate(),
                                                                                            cuaHangId);
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

    public DoanhThuTongQuanResponse getDoanhThuTongQuanInRangeDate(AbstractBaoCaoRequest request) {
        DoanhThuTongQuanResponse response = new DoanhThuTongQuanResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_DOANH_THU_TONG_QUAN_IN_RANGE_DATE_FAIL);
        try {
            if (!Objects.isNull(request)) {
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getCuaHangId(),
                                                                            request.getFromDate(), request.getToDate());
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
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                total = total.add(order.getTotalMoney());
                if (order.getDiscountMoney() != null) {
                    discount = discount.add(order.getDiscountMoney());
                }
                if (order.getRefunds() != null) {
                    refunds = refunds.add(order.getRefunds());
                }
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

    public DoanhThuTheoDanhMucResponse getDoanhThuTheoDanhMuc(String cuaHangId, String currentDate) {
        DoanhThuTheoDanhMucResponse response = new DoanhThuTheoDanhMucResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_DOANH_THU_THEO_DANH_MUC_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String theDayBefore = DateUtils.getDayBeforeTheGivenDay(currentDate);
                List<Order> listOrdersCurrentDay = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                List<Order> listOrdersTheDayBefore = orderRepository.findAllOrderByCreatedDateAndCuaHangId(theDayBefore, cuaHangId);
                List<DanhMucBanChay> listDanhMucBanChayCurrentDay = new ArrayList<DanhMucBanChay>();
                List<DanhMucBanChay> listDanhMucBanChayTheDayBefore = new ArrayList<DanhMucBanChay>();
                if (CollectionUtils.isNotEmpty(listOrdersCurrentDay)) {
                    listDanhMucBanChayCurrentDay = analysisDoanhThuTheoDanhMuc(listOrdersCurrentDay);
                }
                if (CollectionUtils.isNotEmpty(listOrdersTheDayBefore)) {
                    listDanhMucBanChayTheDayBefore = analysisDoanhThuTheoDanhMuc(listOrdersTheDayBefore);
                }
                List<DanhMucBanChay> result = calculateDoanhThuTheoDanhMucRevenuePercentage(listDanhMucBanChayCurrentDay, listDanhMucBanChayTheDayBefore);
                if (!result.isEmpty()) {
                    response.setCuaHangId(cuaHangId);
                    response.setListDanhMucBanChay(result);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when getDoanhThuTheoDanhMuc: {}", e);
        }
        return response;
    }

    public DoanhThuTheoDanhMucResponse getDoanhThuTheoDanhMucInRangeDate(AbstractBaoCaoRequest request) {
        DoanhThuTheoDanhMucResponse response = new DoanhThuTheoDanhMucResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_DOANH_THU_THEO_DANH_MUC_FAIL);
        if (request != null) {
            String fromDate = request.getFromDate();
            String toDate = request.getToDate();
            List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getCuaHangId(), fromDate, toDate);
            if (CollectionUtils.isNotEmpty(orders)) {
                response.setCuaHangId(request.getCuaHangId());
                List<DanhMucBanChay> listDanhMucBanChay = analysisDoanhThuTheoDanhMuc(orders);
                response.setListDanhMucBanChay(listDanhMucBanChay);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            }
        }
        return response;
    }

    private List<DanhMucBanChay> analysisDoanhThuTheoDanhMuc(List<Order> orders) {
        List<DanhMucBanChay> listDanhMucBanChay = new ArrayList<DanhMucBanChay>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                List<MatHangTieuThu> listMatHangTieuThu = order.getListMatHangTieuThu();
                for (MatHangTieuThu temp : listMatHangTieuThu) {
                    String matHangName = temp.getMatHangName() + " (" + temp.getBangGiaName() + ") ";
                    Optional<Category> optDanhMuc = categoryRepository.findCategoryByCategoryIdAndRestaurantId(temp.getDanhMucId(), order.getCuaHangId());
                    if (optDanhMuc.isPresent()) {
                        //Check danh muc existed List<DanhMucBanChay>
                        if (!checkDanhMucExistInListDanhMucBanChay(listDanhMucBanChay, optDanhMuc.get())) {
                            DanhMucBanChay danhMucBanChay = new DanhMucBanChay();
                            List<MatHangBanChay> listMatHangBanChay = new ArrayList<MatHangBanChay>();
                            MatHangBanChay matHangBanChay = new MatHangBanChay();
                            matHangBanChay.setName(matHangName);
                            matHangBanChay.setQuantity(temp.getQuantity());
                            matHangBanChay.setTotalPrice(temp.getUnitPrice().multiply(BigDecimal.valueOf((long) temp.getQuantity())));
                            listMatHangBanChay.add(matHangBanChay);
                            Map<String, BigDecimal> totalPriceAndQuantity = calculateTotalPriceAndQuantityListMatHangBanChay(listMatHangBanChay);
                            danhMucBanChay.setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                            danhMucBanChay.setTotalQuantity(totalPriceAndQuantity.get(QUANTITY).intValue());
                            danhMucBanChay.setDanhMucName(optDanhMuc.get().getName());
                            danhMucBanChay.setListMatHangBanChay(listMatHangBanChay);
                            listDanhMucBanChay.add(danhMucBanChay);
                        } else {
                            int index = IntStream.range(0, listDanhMucBanChay.size()).filter(i ->
                                    optDanhMuc.get().getName().equalsIgnoreCase(listDanhMucBanChay.get(i).getDanhMucName())).findFirst().getAsInt();
                            List<MatHangBanChay> listMatHangBanChay = listDanhMucBanChay.get(index).getListMatHangBanChay();
                            boolean isMatHangBanChayExisted = checkMatHangBanChayExist(listMatHangBanChay, matHangName);
                            if (isMatHangBanChayExisted) {
                                int indexMatHang = IntStream.range(0, listMatHangBanChay.size()).filter(i ->
                                        matHangName.equalsIgnoreCase(listMatHangBanChay.get(i).getName())).findFirst().getAsInt();
                                int quantity = listMatHangBanChay.get(indexMatHang).getQuantity();
                                listMatHangBanChay.get(indexMatHang).setQuantity(quantity + temp.getQuantity());
                                BigDecimal price = listMatHangBanChay.get(indexMatHang).getTotalPrice();
                                listMatHangBanChay.get(indexMatHang).setTotalPrice(
                                        price.add(temp.getUnitPrice().multiply(BigDecimal.valueOf(temp.getQuantity()))));
                            } else {
                                MatHangBanChay matHangBanChay = new MatHangBanChay();
                                matHangBanChay.setName(matHangName);
                                matHangBanChay.setQuantity(temp.getQuantity());
                                matHangBanChay.setTotalPrice(temp.getUnitPrice());
                                listMatHangBanChay.add(matHangBanChay);
                            }
                            Map<String, BigDecimal> totalPriceAndQuantity = calculateTotalPriceAndQuantityListMatHangBanChay(listMatHangBanChay);
                            listDanhMucBanChay.get(index).setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                            listDanhMucBanChay.get(index).setTotalQuantity(totalPriceAndQuantity.get(QUANTITY).intValue());
                        }
                    }
                }
            }
        }
        return listDanhMucBanChay;
    }

    private boolean checkDanhMucExistInListDanhMucBanChay(List<DanhMucBanChay> listDanhMucBanChay, Category category) {
        boolean isExisted = false;
        if (listDanhMucBanChay.stream().anyMatch(tmp -> tmp.getDanhMucName().equalsIgnoreCase(category.getName()))) {
            isExisted = true;
        }
        return isExisted;
    }

    private Map<String, BigDecimal> calculateTotalPriceAndQuantityListMatHangBanChay(List<MatHangBanChay> listMatHangBanChay) {
        Map<String, BigDecimal> result = new HashMap<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        int quantity = 0;
        for (MatHangBanChay matHangBanChay : listMatHangBanChay) {
            totalPrice = totalPrice.add(matHangBanChay.getTotalPrice());
            quantity += matHangBanChay.getQuantity();
        }
        result.put(TOTAL_PRICE, totalPrice);
        result.put(QUANTITY, BigDecimal.valueOf((double) quantity));
        return result;
    }

    private List<DanhMucBanChay> calculateDoanhThuTheoDanhMucRevenuePercentage(List<DanhMucBanChay> ordersCurrentDay, List<DanhMucBanChay> ordersTheDayBefore) {
        List<DanhMucBanChay> result = new ArrayList<DanhMucBanChay>();
        if (ordersCurrentDay.size() > 0 && ordersTheDayBefore.size() > 0) {
            for (DanhMucBanChay danhMucBanChayCurrenDay : ordersCurrentDay) {
                boolean isFounded = false;
                for (DanhMucBanChay danhMucBanChayTheDayBefore : ordersTheDayBefore) {
                    if (danhMucBanChayTheDayBefore.getDanhMucName().equalsIgnoreCase(danhMucBanChayCurrenDay.getDanhMucName())) {
                        isFounded = true;
                        DanhMucBanChay temp = new DanhMucBanChay();
                        temp.setDanhMucName(danhMucBanChayCurrenDay.getDanhMucName());
                        temp.setTotalQuantity(danhMucBanChayCurrenDay.getTotalQuantity());
                        temp.setTotalPrice(danhMucBanChayCurrenDay.getTotalPrice());
                        BigDecimal revenuePercentage = ((danhMucBanChayCurrenDay.getTotalPrice().subtract(danhMucBanChayTheDayBefore.getTotalPrice())).multiply(BigDecimal.valueOf(100))).divide(danhMucBanChayTheDayBefore.getTotalPrice(), 2, RoundingMode.HALF_UP);
                        temp.setRevenuePercentage(revenuePercentage + "%");
                        if (revenuePercentage.compareTo(BigDecimal.ZERO) > 0) {
                            temp.setStatus(RevenuePercentageStatusType.INCREASE);
                        } else {
                            temp.setStatus(RevenuePercentageStatusType.DECREASE);
                        }
                        List<MatHangBanChay> listMatHangBanChay = calculateMatHangBanChayRevenuePercentage(danhMucBanChayCurrenDay.getListMatHangBanChay(), danhMucBanChayTheDayBefore.getListMatHangBanChay());
                        temp.setListMatHangBanChay(listMatHangBanChay);
                        result.add(temp);
                        break;
                    }
                }
                if (!isFounded) {
                    DanhMucBanChay temp = new DanhMucBanChay();
                    temp.setDanhMucName(danhMucBanChayCurrenDay.getDanhMucName());
                    temp.setTotalQuantity(danhMucBanChayCurrenDay.getTotalQuantity());
                    temp.setStatus(RevenuePercentageStatusType.INCREASE_INFINITY);
                    temp.setTotalPrice(danhMucBanChayCurrenDay.getTotalPrice());
                    for (MatHangBanChay matHangBanChay : danhMucBanChayCurrenDay.getListMatHangBanChay()) {
                        matHangBanChay.setStatus(RevenuePercentageStatusType.INCREASE_INFINITY);
                    }
                    temp.setListMatHangBanChay(danhMucBanChayCurrenDay.getListMatHangBanChay());
                    result.add(temp);
                }
            }
        } else if (ordersCurrentDay.size() == 0 && ordersTheDayBefore.size() > 0) {
            for (DanhMucBanChay danhMucBanChay : ordersTheDayBefore) {
                danhMucBanChay.setTotalPrice(BigDecimal.ZERO);
                danhMucBanChay.setTotalQuantity(ZERO_NUMBER);
                danhMucBanChay.setListMatHangBanChay(new ArrayList<MatHangBanChay>());
                danhMucBanChay.setStatus(RevenuePercentageStatusType.DECREASE);
                danhMucBanChay.setRevenuePercentage(ONE_HUNDRED_PERCENT);
            }
            result = ordersTheDayBefore;
        } else if (ordersCurrentDay.size() > 0 && ordersTheDayBefore.size() == 0) {
            for (DanhMucBanChay danhMucBanChay : ordersCurrentDay) {
                danhMucBanChay.setStatus(RevenuePercentageStatusType.INCREASE_INFINITY);
            }
            result = ordersCurrentDay;
        }
        for (DanhMucBanChay danhMucBanChayTheDayBefore : ordersTheDayBefore) {
            boolean isFounded = false;
            for (DanhMucBanChay danhMucBanChayInResult : result) {
                if (danhMucBanChayInResult.getDanhMucName().equalsIgnoreCase(danhMucBanChayTheDayBefore.getDanhMucName())) {
                    isFounded = true;
                    break;
                }
            }
            if (!isFounded) {
                DanhMucBanChay temp = new DanhMucBanChay();
                temp.setDanhMucName(danhMucBanChayTheDayBefore.getDanhMucName());
                temp.setTotalQuantity(ZERO_NUMBER);
                temp.setStatus(RevenuePercentageStatusType.DECREASE);
                temp.setTotalPrice(BigDecimal.ZERO);
                temp.setRevenuePercentage(ONE_HUNDRED_PERCENT);
                result.add(temp);
            }
        }
        return result;
    }

    public MatHangBanChayResponse getMatHangBanChay(String cuaHangId, String currentDate) {
        MatHangBanChayResponse response = new MatHangBanChayResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_MAT_HANG_BAN_CHAY_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String theDayBefore = DateUtils.getDayBeforeTheGivenDay(currentDate);
                List<Order> listOrdersCurrentDay = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                List<Order> listOrdersTheDayBefore = orderRepository.findAllOrderByCreatedDateAndCuaHangId(theDayBefore, cuaHangId);
                List<MatHangBanChay> listMatHangBanChayCurrentDay = new ArrayList<MatHangBanChay>();
                List<MatHangBanChay> listMatHangBanChayTheDayBefore = new ArrayList<MatHangBanChay>();
                if (CollectionUtils.isNotEmpty(listOrdersCurrentDay)) {
                    listMatHangBanChayCurrentDay = analysisMatHangBanChay(listOrdersCurrentDay);
                }
                if (CollectionUtils.isNotEmpty(listOrdersTheDayBefore)) {
                    listMatHangBanChayTheDayBefore = analysisMatHangBanChay(listOrdersTheDayBefore);
                }
                List<MatHangBanChay> result = calculateMatHangBanChayRevenuePercentage(listMatHangBanChayCurrentDay, listMatHangBanChayTheDayBefore);
                if (!result.isEmpty()) {
                    response.setCuaHangId(cuaHangId);
                    response.setMatHangBanChayList(result);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when getMatHangBanChay: {}", e);
        }
        return response;
    }

    public MatHangBanChayResponse getMatHangBanChayInRangeDate(AbstractBaoCaoRequest request) {
        MatHangBanChayResponse response = new MatHangBanChayResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_MAT_HANG_BAN_CHAY_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getCuaHangId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(request.getCuaHangId());
                    List<MatHangBanChay> matHangBanChayList = analysisMatHangBanChay(orders);
                    response.setMatHangBanChayList(matHangBanChayList);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when getMatHangBanChayInRangeDate: {}", e);
        }
        return response;
    }

    private List<MatHangBanChay> analysisMatHangBanChay(List<Order> orders) {
        List<MatHangBanChay> listMatHangBanChay = new ArrayList<MatHangBanChay>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                List<MatHangTieuThu> matHangTieuThuList = order.getListMatHangTieuThu();
                for (MatHangTieuThu matHangTieuThu : matHangTieuThuList) {
                    int quantity = matHangTieuThu.getQuantity();
                    BigDecimal price = matHangTieuThu.getUnitPrice().multiply(BigDecimal.valueOf((double) matHangTieuThu.getQuantity()));
                    String matHangName = matHangTieuThu.getMatHangName() + " (" + matHangTieuThu.getBangGiaName() + ") ";
                    if (checkMatHangBanChayExist(listMatHangBanChay, matHangName)) {
                        int indexMatHang = IntStream.range(0, listMatHangBanChay.size()).filter(i ->
                                matHangName.equalsIgnoreCase(listMatHangBanChay.get(i).getName())).findFirst().getAsInt();
                        int originalQuantity = listMatHangBanChay.get(indexMatHang).getQuantity();
                        listMatHangBanChay.get(indexMatHang).setQuantity(quantity + originalQuantity);
                        BigDecimal originalPrice = listMatHangBanChay.get(indexMatHang).getTotalPrice();
                        listMatHangBanChay.get(indexMatHang).setTotalPrice(price.add(originalPrice));
                    } else {
                        MatHangBanChay matHangBanChay = new MatHangBanChay();
                        matHangBanChay.setQuantity(quantity);
                        matHangBanChay.setTotalPrice(price);
                        matHangBanChay.setName(matHangName);
                        listMatHangBanChay.add(matHangBanChay);
                    }
                }
            }
        }
        return listMatHangBanChay;
    }

    private boolean checkMatHangBanChayExist(List<MatHangBanChay> listMatHangBanChay, String matHangName) {
        boolean isExisted = false;
        if (listMatHangBanChay.stream().anyMatch(tmp -> tmp.getName().equalsIgnoreCase(matHangName))) {
            isExisted = true;
        }
        return isExisted;
    }

    private List<MatHangBanChay> calculateMatHangBanChayRevenuePercentage(List<MatHangBanChay> listCurrentDay, List<MatHangBanChay> listTheDayBefore) {
        List<MatHangBanChay> result = new ArrayList<MatHangBanChay>();
        if (listCurrentDay.size() > 0 && listTheDayBefore.size() > 0) {
            for (MatHangBanChay matHangBanChayCurrentDay : listCurrentDay) {
                boolean isFounded = false;
                for (MatHangBanChay matHangBanChayTheDayBefore : listTheDayBefore) {
                    if (matHangBanChayTheDayBefore.getName().equalsIgnoreCase(matHangBanChayCurrentDay.getName())) {
                        isFounded = true;
                        MatHangBanChay temp = new MatHangBanChay();
                        temp.setName(matHangBanChayCurrentDay.getName());
                        temp.setQuantity(matHangBanChayCurrentDay.getQuantity());
                        temp.setTotalPrice(matHangBanChayCurrentDay.getTotalPrice());
                        BigDecimal theDayBeforePrice = matHangBanChayTheDayBefore.getTotalPrice();
                        BigDecimal revenuePercentage = ((matHangBanChayCurrentDay.getTotalPrice().subtract(theDayBeforePrice)).multiply(BigDecimal.valueOf(100))).divide(theDayBeforePrice, 2, RoundingMode.HALF_UP);
                        temp.setRevenuePercentage(revenuePercentage + "%");
                        if (revenuePercentage.compareTo(BigDecimal.ZERO) > 0) {
                            temp.setStatus(RevenuePercentageStatusType.INCREASE);
                        } else if (revenuePercentage.compareTo(BigDecimal.ZERO) == 0) {
                            temp.setStatus(RevenuePercentageStatusType.NORMAL);
                        } else {
                            temp.setStatus(RevenuePercentageStatusType.DECREASE);
                        }
                        result.add(temp);
                    }
                }
                if (!isFounded) {
                    MatHangBanChay matHangBanChay = new MatHangBanChay();
                    matHangBanChay.setName(matHangBanChayCurrentDay.getName());
                    matHangBanChay.setTotalPrice(matHangBanChayCurrentDay.getTotalPrice());
                    matHangBanChay.setQuantity(matHangBanChayCurrentDay.getQuantity());
                    matHangBanChay.setStatus(RevenuePercentageStatusType.INCREASE_INFINITY);
                    result.add(matHangBanChay);
                }
            }
        } else if (listCurrentDay.size() == 0 && listTheDayBefore.size() > 0) {
            for (MatHangBanChay matHangBanChay : listTheDayBefore) {
                matHangBanChay.setTotalPrice(BigDecimal.ZERO);
                matHangBanChay.setQuantity(ZERO_NUMBER);
                matHangBanChay.setStatus(RevenuePercentageStatusType.DECREASE);
                matHangBanChay.setRevenuePercentage(ONE_HUNDRED_PERCENT);
            }
            result = listTheDayBefore;
        } else if (listCurrentDay.size() > 0 && listTheDayBefore.size() == 0) {
            for (MatHangBanChay matHangBanChay : listCurrentDay) {
                matHangBanChay.setStatus(RevenuePercentageStatusType.INCREASE_INFINITY);
            }
            result = listCurrentDay;
        }
        for (MatHangBanChay matHangBanChayTheDayBefore : listTheDayBefore) {
            boolean isFounded = false;
            for (MatHangBanChay matHangBanChayInResult : result) {
                if (matHangBanChayInResult.getName().equalsIgnoreCase(matHangBanChayTheDayBefore.getName())) {
                    isFounded = true;
                    break;
                }
            }
            if (!isFounded) {
                MatHangBanChay matHangBanChay = new MatHangBanChay();
                matHangBanChay.setName(matHangBanChayTheDayBefore.getName());
                matHangBanChay.setTotalPrice(BigDecimal.ZERO);
                matHangBanChay.setQuantity(ZERO_NUMBER);
                matHangBanChay.setStatus(RevenuePercentageStatusType.DECREASE);
                matHangBanChay.setRevenuePercentage(ONE_HUNDRED_PERCENT);
                result.add(matHangBanChay);
            }
        }
        return result;
    }

    public BaoCaoGiamGiaResponse getBaoCaoGiamGia(String cuaHangId, String currentDate) {
        BaoCaoGiamGiaResponse response = new BaoCaoGiamGiaResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_GIAM_GIA_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(cuaHangId);
                    List<BaoCaoGiamGia> baoCaoGiamGiaList = analysisBaoCaoGiamGia(orders);
                    response.setListGiamGiaReport(baoCaoGiamGiaList);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when getBaoCaoGiamGia: {}", e);
        }
        return response;
    }

    public BaoCaoGiamGiaResponse getBaoCaoGiamGiaInRangeDate(AbstractBaoCaoRequest request) {
        BaoCaoGiamGiaResponse response = new BaoCaoGiamGiaResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_GIAM_GIA_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getCuaHangId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(request.getCuaHangId());
                    List<BaoCaoGiamGia> baoCaoGiamGiaList = analysisBaoCaoGiamGia(orders);
                    response.setListGiamGiaReport(baoCaoGiamGiaList);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when getBaoCaoGiamGiaInRangeDate: {}", e);
        }
        return response;
    }

    private List<BaoCaoGiamGia> analysisBaoCaoGiamGia(List<Order> orders) {
        List<BaoCaoGiamGia> listBaoCaoGiamGia = new ArrayList<BaoCaoGiamGia>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                BaoCaoGiamGia baoCaoGiamGia = new BaoCaoGiamGia();
                if (!order.getGiamGiaType().equals(GiamGiaType.NONE)) {
                    String giamGiaName = order.getGiamGiaName();
                    String baoCaoGiamGiaName = "";
                    if (order.getGiamGiaType().label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THEO_DANH_MỤC.label)) {
                        String percent = order.getGiamGiaPercentage() + "%";
                        baoCaoGiamGiaName = giamGiaName + " (-" + percent + ")";
                    } else if (order.getGiamGiaType().label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THÔNG_THƯỜNG.label)) {
                        if (order.getGiamGiaDiscountAmount() != null) {
                            String discountAmount = order.getGiamGiaDiscountAmount() + "đ";
                            baoCaoGiamGiaName = giamGiaName + " (-" + discountAmount + ")";
                        } else if (order.getGiamGiaPercentage() != null) {
                            String percent = order.getGiamGiaPercentage() + "%";
                            baoCaoGiamGiaName = giamGiaName + " (-" + percent + ")";
                        }
                    }
                    if (checkBaoCaoGiamGiaExist(listBaoCaoGiamGia, baoCaoGiamGiaName)) {
                        String finalBaoCaoGiamGiaName = baoCaoGiamGiaName;
                        int index = IntStream.range(0, listBaoCaoGiamGia.size()).filter(i ->
                                finalBaoCaoGiamGiaName.equalsIgnoreCase(listBaoCaoGiamGia.get(i).getName())).findFirst().getAsInt();
                        listBaoCaoGiamGia.get(index).setTotalQuantity(listBaoCaoGiamGia.get(index).getTotalQuantity() + 1);
                        List<BaoCaoGiamGiaDetail> giamGiaDetails = listBaoCaoGiamGia.get(index).getListDiscountInvoice();
                        BaoCaoGiamGiaDetail temp = new BaoCaoGiamGiaDetail();
                        temp.setDiscountPrice(order.getDiscountMoney());
                        temp.setReceiptCode(order.getReceiptCode());
                        temp.setTime(order.getOrderTimeCheckOut());
                        giamGiaDetails.add(temp);
                        listBaoCaoGiamGia.get(index).setListDiscountInvoice(giamGiaDetails);
                        BigDecimal totalDiscount = calculateDiscountMoney(giamGiaDetails);
                        listBaoCaoGiamGia.get(index).setTotalDiscount(totalDiscount);
                    } else {
                        baoCaoGiamGia.setTotalQuantity(1);
                        baoCaoGiamGia.setName(baoCaoGiamGiaName);
                        List<BaoCaoGiamGiaDetail> giamGiaDetails = new ArrayList<BaoCaoGiamGiaDetail>();
                        BaoCaoGiamGiaDetail temp = new BaoCaoGiamGiaDetail();
                        temp.setDiscountPrice(order.getDiscountMoney());
                        temp.setReceiptCode(order.getReceiptCode());
                        temp.setTime(order.getOrderTimeCheckOut());
                        giamGiaDetails.add(temp);
                        baoCaoGiamGia.setListDiscountInvoice(giamGiaDetails);
                        listBaoCaoGiamGia.add(baoCaoGiamGia);
                        BigDecimal totalDiscount = calculateDiscountMoney(giamGiaDetails);
                        baoCaoGiamGia.setTotalDiscount(totalDiscount);
                    }
                }
            }
        }
        return listBaoCaoGiamGia;
    }

    private boolean checkBaoCaoGiamGiaExist(List<BaoCaoGiamGia> listBaoCaoGiamGia, String baoCaoGiamGiaName) {
        boolean isExisted = false;
        if (listBaoCaoGiamGia.stream().anyMatch(tmp -> tmp.getName().equalsIgnoreCase(baoCaoGiamGiaName))) {
            isExisted = true;
        }
        return isExisted;
    }

    private BigDecimal calculateDiscountMoney(List<BaoCaoGiamGiaDetail> giamGiaDetails) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        for (BaoCaoGiamGiaDetail temp : giamGiaDetails) {
            totalDiscount = totalDiscount.add(temp.getDiscountPrice());
        }
        return totalDiscount;
    }

    public DoanhThuTheoNhanVienResponse getBaoCaoDoanhThuTheoNhanVien(String cuaHangId, String currentDate) {
        DoanhThuTheoNhanVienResponse response = new DoanhThuTheoNhanVienResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_DOANH_THU_THEO_NHAN_VIEN_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(cuaHangId);
                    List<DoanhThuTheoNhanVien> listDoanhThuTheoNhanVien = analysisDoanhThuTheoNhanVien(orders);
                    response.setListEmployeeSRevenue(listDoanhThuTheoNhanVien);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when getBaoCaoDoanhThuTheoNhanVien: {}", e);
        }
        return response;
    }

    public DoanhThuTheoNhanVienResponse getBaoCaoDoanhThuTheoNhanVienInRangeDate(AbstractBaoCaoRequest request) {
        DoanhThuTheoNhanVienResponse response = new DoanhThuTheoNhanVienResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_DOANH_THU_THEO_NHAN_VIEN_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getCuaHangId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(request.getCuaHangId());
                    List<DoanhThuTheoNhanVien> listDoanhThuTheoNhanVien = analysisDoanhThuTheoNhanVien(orders);
                    response.setListEmployeeSRevenue(listDoanhThuTheoNhanVien);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when getBaoCaoDoanhThuTheoNhanVienInRangeDate: {}", e);
        }
        return response;
    }

    private List<DoanhThuTheoNhanVien> analysisDoanhThuTheoNhanVien(List<Order> orders) {
        List<DoanhThuTheoNhanVien> listDoanhThuTheoNhanVien = new ArrayList<DoanhThuTheoNhanVien>();
        for (Order order : orders) {
            if (order.getOrderStatus().toString().equalsIgnoreCase(OrderStatus.SUCCESS.toString())) {
                if (checkWaiterNameIsExisted(listDoanhThuTheoNhanVien, order.getWaiterName())) {
                    int index = IntStream.range(0, listDoanhThuTheoNhanVien.size()).filter(i ->
                            order.getWaiterName().equalsIgnoreCase(listDoanhThuTheoNhanVien.get(i).getEmployeeName())).findFirst().getAsInt();
                    OrdersWerePaidInformation orderPaidInformation = new OrdersWerePaidInformation();
                    orderPaidInformation.setReceiptCode(order.getReceiptCode());
                    orderPaidInformation.setTime(order.getOrderTimeCheckOut());
                    orderPaidInformation.setTotalPrice(order.getTotalMoney());
                    listDoanhThuTheoNhanVien.get(index).getListOrdersWerePaidInfor().add(orderPaidInformation);
                    Map<String, BigDecimal> totalPriceAndQuantity = calculateDoanhThuTheoNhanVienTotalPrice(listDoanhThuTheoNhanVien.get(index).getListOrdersWerePaidInfor());
                    listDoanhThuTheoNhanVien.get(index).setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                    listDoanhThuTheoNhanVien.get(index).setTotalOrderSuccess(totalPriceAndQuantity.get(QUANTITY).intValue());
                } else {
                    DoanhThuTheoNhanVien doanhThuTheoNhanVien = new DoanhThuTheoNhanVien();
                    doanhThuTheoNhanVien.setEmployeeName(order.getWaiterName());
                    List<OrdersWerePaidInformation> listOrdersWerePaidInformations = new ArrayList<OrdersWerePaidInformation>();
                    OrdersWerePaidInformation orderPaidInformation = new OrdersWerePaidInformation();
                    orderPaidInformation.setReceiptCode(order.getReceiptCode());
                    orderPaidInformation.setTime(order.getOrderTimeCheckOut());
                    orderPaidInformation.setTotalPrice(order.getTotalMoney());
                    listOrdersWerePaidInformations.add(orderPaidInformation);
                    doanhThuTheoNhanVien.setListOrdersWerePaidInfor(listOrdersWerePaidInformations);
                    Map<String, BigDecimal> totalPriceAndQuantity = calculateDoanhThuTheoNhanVienTotalPrice(listOrdersWerePaidInformations);
                    doanhThuTheoNhanVien.setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                    doanhThuTheoNhanVien.setTotalOrderSuccess(totalPriceAndQuantity.get(QUANTITY).intValue());
                    listDoanhThuTheoNhanVien.add(doanhThuTheoNhanVien);
                }

            }
        }
        return listDoanhThuTheoNhanVien;
    }

    private boolean checkWaiterNameIsExisted(List<DoanhThuTheoNhanVien> listDoanhThuTheoNhanVien, String waiterName) {
        boolean isExisted = false;
        if (listDoanhThuTheoNhanVien.stream().anyMatch(tmp -> tmp.getEmployeeName().equalsIgnoreCase(waiterName))) {
            isExisted = true;
        }
        return isExisted;
    }

    private Map<String, BigDecimal> calculateDoanhThuTheoNhanVienTotalPrice(List<OrdersWerePaidInformation> listOrdersWerePaidInformations) {
        Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrdersWerePaidInformation temp : listOrdersWerePaidInformations) {
            totalPrice = totalPrice.add(temp.getTotalPrice());
        }
        result.put(TOTAL_PRICE, totalPrice);
        result.put(QUANTITY, BigDecimal.valueOf((long) listOrdersWerePaidInformations.size()));
        return result;
    }
}
