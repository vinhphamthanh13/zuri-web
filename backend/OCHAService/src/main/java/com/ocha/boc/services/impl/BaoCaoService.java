package com.ocha.boc.services.impl;

import com.ocha.boc.entity.*;
import com.ocha.boc.repository.DanhMucRepository;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.AbstractBaoCaoRequest;
import com.ocha.boc.response.DoanhThuTheoDanhMucResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@Slf4j
public class BaoCaoService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

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

    public DoanhThuTongQuanResponse getDoanhThuTongQuanInRangeDate(AbstractBaoCaoRequest request) {
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

    public DoanhThuTheoDanhMucResponse getDoanhThuTheoDanhMuc(String cuaHangId) {
        DoanhThuTheoDanhMucResponse response = new DoanhThuTheoDanhMucResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_DOANH_THU_THEO_DANH_MUC_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String currentDate = DateUtils.getCurrentDate();
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(cuaHangId);
                    List<DanhMucBanChay> listDanhMucBanChay = analysisDoanhThuTheoDanhMuc(orders, response);
                    response.setListDanhMucBanChay(listDanhMucBanChay);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when getDoanhThuTheoDanhMuc: {}", e);
        }
        return response;
    }

    private List<DanhMucBanChay> analysisDoanhThuTheoDanhMuc(List<Order> orders, DoanhThuTheoDanhMucResponse response) {
        List<DanhMucBanChay> listDanhMucBanChay = new ArrayList<DanhMucBanChay>();
        for (Order order : orders) {
            List<MatHangTieuThu> listMatHangTieuThu = order.getListMatHangTieuThu();
            DanhMucBanChay danhMucBanChay = new DanhMucBanChay();
            for (MatHangTieuThu temp : listMatHangTieuThu) {
                String bangGiaName = temp.getBangGia().getName();
                MatHang matHang = temp.getMatHang();

                String matHangName = matHang.getName();
                DanhMuc danhMuc = danhMucRepository.findDanhMucByDanhMucIdAndCuaHangId(matHang.getDanhMucId(), matHang.getCuaHangId());
                if (danhMuc != null) {
                    //Check danh muc existed List<DanhMucBanChay>
                    boolean isExisted = checkDanhMucExistInListDanhMucBanChay(listDanhMucBanChay, danhMuc);
                    if (!isExisted) {
                        List<MatHangBanChay> listMatHangBanChay = new ArrayList<MatHangBanChay>();
                        MatHangBanChay matHangBanChay = new MatHangBanChay();
                        matHangBanChay.setName(matHangName);
                        matHangBanChay.setQuantity(temp.getQuantity());
                        matHangBanChay.setTotalPrice(temp.getBangGia().getLoaiGia().getPrice());
                        listMatHangBanChay.add(matHangBanChay);
                        BigDecimal totalPrice = calculateTotalPriceListMatHangBanChay(listMatHangBanChay);
                        danhMucBanChay.setDanhMucName(danhMuc.getName());
                        danhMucBanChay.setTotalPrice(totalPrice);
                        danhMucBanChay.setListMatHangBanChay(listMatHangBanChay);
                        listDanhMucBanChay.add(danhMucBanChay);
                    } else {
                        int index = IntStream.range(0, listDanhMucBanChay.size()).filter(i ->
                                danhMuc.getName().equalsIgnoreCase(listDanhMucBanChay.get(i).getDanhMucName())).findFirst().getAsInt();
                        List<MatHangBanChay> listMatHangBanChay = listDanhMucBanChay.get(index).getListMatHangBanChay();
                        boolean isMatHangBanChayExisted = checkMatHangBanChayExist(listMatHangBanChay, matHangName);
                        if (isMatHangBanChayExisted) {
                            int indexMatHang = IntStream.range(0, listMatHangBanChay.size()).filter(i ->
                                    matHangName.equalsIgnoreCase(listMatHangBanChay.get(i).getName())).findFirst().getAsInt();
                            int quantity = listMatHangBanChay.get(indexMatHang).getQuantity();
                            listMatHangBanChay.get(indexMatHang).setQuantity(quantity + temp.getQuantity());
                            BigDecimal price = listMatHangBanChay.get(indexMatHang).getTotalPrice();
                            listMatHangBanChay.get(indexMatHang).setTotalPrice(
                                    price.add(temp.getBangGia().getLoaiGia().getPrice().multiply(BigDecimal.valueOf(temp.getQuantity()))));
                        } else {
                            MatHangBanChay matHangBanChay = new MatHangBanChay();
                            matHangBanChay.setName(matHangName);
                            matHangBanChay.setQuantity(temp.getQuantity());
                            matHangBanChay.setTotalPrice(temp.getBangGia().getLoaiGia().getPrice());
                            listMatHangBanChay.add(matHangBanChay);
                        }
                        BigDecimal totalPrice = calculateTotalPriceListMatHangBanChay(listMatHangBanChay);
                        listDanhMucBanChay.get(index).setTotalPrice(totalPrice);
                    }
                }
            }
        }

        return listDanhMucBanChay;
    }

    private boolean checkDanhMucExistInListDanhMucBanChay(List<DanhMucBanChay> listDanhMucBanChay, DanhMuc danhMuc) {
        boolean isExisted = false;
        if (listDanhMucBanChay.stream().filter(tmp -> tmp.getDanhMucName().equalsIgnoreCase(danhMuc.getName())).findFirst().isPresent()) {
            isExisted = true;
        }
        return isExisted;
    }

    private boolean checkMatHangBanChayExist(List<MatHangBanChay> listMatHangBanChay, String matHangName) {
        boolean isExisted = false;
        if (listMatHangBanChay.stream().filter(tmp -> tmp.getName().equalsIgnoreCase(matHangName)).findFirst().isPresent()) {
            isExisted = true;
        }
        return isExisted;
    }

    private BigDecimal calculateTotalPriceListMatHangBanChay(List<MatHangBanChay> listMatHangBanChay) {
        BigDecimal result = BigDecimal.ZERO;
        for (MatHangBanChay matHangBanChay : listMatHangBanChay) {
            result = result.add(matHangBanChay.getTotalPrice());
        }
        return result;
    }


}
