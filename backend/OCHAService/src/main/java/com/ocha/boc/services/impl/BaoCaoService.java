package com.ocha.boc.services.impl;

import com.ocha.boc.entity.*;
import com.ocha.boc.enums.GiamGiaType;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.repository.DanhMucRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@Slf4j
public class BaoCaoService {

    private static final String TOTAL_PRICE = "TOTAL";
    private static final String QUANTITY = "QUANTITY";
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
                    List<DanhMucBanChay> listDanhMucBanChay = analysisDoanhThuTheoDanhMuc(orders);
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
            List<MatHangTieuThu> listMatHangTieuThu = order.getListMatHangTieuThu();

            for (MatHangTieuThu temp : listMatHangTieuThu) {
                DanhMucBanChay danhMucBanChay = new DanhMucBanChay();
                String bangGiaName = temp.getBangGia().getName();
                MatHang matHang = temp.getMatHang();

                String matHangName = matHang.getName() + " (" + bangGiaName + ") ";
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
                        Map<String, BigDecimal> totalPriceAndQuantity = calculateTotalPriceAndQuantityListMatHangBanChay(listMatHangBanChay);
                        for (Map.Entry<String, BigDecimal> entry : totalPriceAndQuantity.entrySet()) {
                            String key = entry.getKey();
                            BigDecimal value = entry.getValue();
                            if (key.equalsIgnoreCase(TOTAL_PRICE)) {
                                danhMucBanChay.setTotalPrice(value);
                            } else if (key.equalsIgnoreCase(QUANTITY)) {
                                danhMucBanChay.setTotalQuantity(value.intValue());
                            }
                        }
                        danhMucBanChay.setDanhMucName(danhMuc.getName());
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
                        Map<String, BigDecimal> totalPriceAndQuantity = calculateTotalPriceAndQuantityListMatHangBanChay(listMatHangBanChay);
                        for (Map.Entry<String, BigDecimal> entry : totalPriceAndQuantity.entrySet()) {
                            String key = entry.getKey();
                            BigDecimal value = entry.getValue();
                            if (key.equalsIgnoreCase(TOTAL_PRICE)) {
                                listDanhMucBanChay.get(index).setTotalPrice(value);
                            } else if (key.equalsIgnoreCase(QUANTITY)) {
                                listDanhMucBanChay.get(index).setTotalQuantity(value.intValue());
                            }
                        }
                    }
                }
            }
        }

        return listDanhMucBanChay;
    }

    private boolean checkDanhMucExistInListDanhMucBanChay(List<DanhMucBanChay> listDanhMucBanChay, DanhMuc danhMuc) {
        boolean isExisted = false;
        if (listDanhMucBanChay.stream().anyMatch(tmp -> tmp.getDanhMucName().equalsIgnoreCase(danhMuc.getName()))) {
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

    public MatHangBanChayResponse getMatHangBanChay(String cuaHangId) {
        MatHangBanChayResponse response = new MatHangBanChayResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_MAT_HANG_BAN_CHAY_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String currentDate = DateUtils.getCurrentDate();
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndCuaHangId(currentDate, cuaHangId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(cuaHangId);
                    List<MatHangBanChay> matHangBanChayList = analysisMatHangBanChay(orders);
                    response.setMatHangBanChayList(matHangBanChayList);
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
            List<MatHangTieuThu> matHangTieuThuList = order.getListMatHangTieuThu();
            for (MatHangTieuThu matHangTieuThu : matHangTieuThuList) {
                int quantity = matHangTieuThu.getQuantity();
                BigDecimal price = matHangTieuThu.getTotal();
                String bangGiaName = "";
                if (matHangTieuThu.getBangGia() != null) {
                    bangGiaName = matHangTieuThu.getBangGia().getName();
                }
                String matHangName = matHangTieuThu.getMatHang().getName() + " (" + bangGiaName + ") ";
                boolean isMatHangBanChayExisted = checkMatHangBanChayExist(listMatHangBanChay, matHangName);
                if (isMatHangBanChayExisted) {
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
        return listMatHangBanChay;
    }

    private boolean checkMatHangBanChayExist(List<MatHangBanChay> listMatHangBanChay, String matHangName) {
        boolean isExisted = false;
        if (listMatHangBanChay.stream().anyMatch(tmp -> tmp.getName().equalsIgnoreCase(matHangName))) {
            isExisted = true;
        }
        return isExisted;
    }

    public BaoCaoGiamGiaResponse getBaoCaoGiamGia(String cuaHangId) {
        BaoCaoGiamGiaResponse response = new BaoCaoGiamGiaResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_GIAM_GIA_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String currentDate = DateUtils.getCurrentDate();
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
            BaoCaoGiamGia baoCaoGiamGia = new BaoCaoGiamGia();
            if (order.getGiamGia() != null) {
                GiamGia giamGia = order.getGiamGia();
                String giamGiaName = giamGia.getName();
                String baoCaoGiamGiaName = "";
                GiamGiaType giamGiaType = giamGia.getGiamGiaType();
                if (giamGiaType.label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THEO_DANH_MỤC.label)) {
                    String percent = giamGia.getPercentage() + "%";
                    baoCaoGiamGiaName = giamGiaName + " (-" + percent + ")";
                } else if (giamGiaType.label.equalsIgnoreCase(GiamGiaType.GIẢM_GIÁ_THÔNG_THƯỜNG.label)) {
                    if (giamGia.getDiscountAmount() != null) {
                        String discountAmount = giamGia.getDiscountAmount() + "đ";
                        baoCaoGiamGiaName = giamGiaName + " (-" + discountAmount + ")";
                    } else if (giamGia.getPercentage() != null) {
                        String percent = giamGia.getPercentage() + "%";
                        baoCaoGiamGiaName = giamGiaName + " (-" + percent + ")";
                    }
                }
                boolean isBaoCaoGiamGiaExisted = checkBaoCaoGiamGiaExist(listBaoCaoGiamGia, baoCaoGiamGiaName);
                if (isBaoCaoGiamGiaExisted) {
                    String finalBaoCaoGiamGiaName = baoCaoGiamGiaName;
                    int index = IntStream.range(0, listBaoCaoGiamGia.size()).filter(i ->
                            finalBaoCaoGiamGiaName.equalsIgnoreCase(listBaoCaoGiamGia.get(i).getName())).findFirst().getAsInt();
                    int quantity = listBaoCaoGiamGia.get(index).getTotalQuantity();
                    listBaoCaoGiamGia.get(index).setTotalQuantity(quantity + 1);
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

    public DoanhThuTheoNhanVienResponse getBaoCaoDoanhThuTheoNhanVien(String cuaHangId) {
        DoanhThuTheoNhanVienResponse response = new DoanhThuTheoNhanVienResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_DOANH_THU_THEO_NHAN_VIEN_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String currentDate = DateUtils.getCurrentDate();
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
            if(request != null){
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getCuaHangId(), fromDate, toDate);
                if(CollectionUtils.isNotEmpty(orders)){
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
                String waiterName = order.getWaiterName();
                boolean isWaiterNameIsExisted = checkWaiterNameIsExisted(listDoanhThuTheoNhanVien, waiterName);
                if (isWaiterNameIsExisted) {
                    int index = IntStream.range(0, listDoanhThuTheoNhanVien.size()).filter(i ->
                            waiterName.equalsIgnoreCase(listDoanhThuTheoNhanVien.get(i).getEmployeeName())).findFirst().getAsInt();
                    OrdersWerePaidInformation orderPaidInformation = new OrdersWerePaidInformation();
                    orderPaidInformation.setReceiptCode(order.getReceiptCode());
                    orderPaidInformation.setTime(order.getOrderTimeCheckOut());
                    orderPaidInformation.setTotalPrice(order.getTotalMoney());
                    listDoanhThuTheoNhanVien.get(index).getListOrdersWerePaidInfor().add(orderPaidInformation);
                    Map<String, BigDecimal> totalPriceAndQuantity = calculateDoanhThuTheoNhanVienTotalPrice(listDoanhThuTheoNhanVien.get(index).getListOrdersWerePaidInfor());
                    for (Map.Entry<String, BigDecimal> entry : totalPriceAndQuantity.entrySet()) {
                        String key = entry.getKey();
                        BigDecimal value = entry.getValue();
                        if (key.equalsIgnoreCase(TOTAL_PRICE)) {
                            listDoanhThuTheoNhanVien.get(index).setTotalPrice(value);
                        } else if (key.equalsIgnoreCase(QUANTITY)) {
                            listDoanhThuTheoNhanVien.get(index).setTotalOrderSuccess(value.intValue());
                        }
                    }
                } else {
                    DoanhThuTheoNhanVien doanhThuTheoNhanVien = new DoanhThuTheoNhanVien();
                    doanhThuTheoNhanVien.setEmployeeName(waiterName);
                    List<OrdersWerePaidInformation> listOrdersWerePaidInformations = new ArrayList<OrdersWerePaidInformation>();
                    OrdersWerePaidInformation orderPaidInformation = new OrdersWerePaidInformation();
                    orderPaidInformation.setReceiptCode(order.getReceiptCode());
                    orderPaidInformation.setTime(order.getOrderTimeCheckOut());
                    orderPaidInformation.setTotalPrice(order.getTotalMoney());
                    listOrdersWerePaidInformations.add(orderPaidInformation);
                    doanhThuTheoNhanVien.setListOrdersWerePaidInfor(listOrdersWerePaidInformations);
                    Map<String, BigDecimal> totalPriceAndQuantity = calculateDoanhThuTheoNhanVienTotalPrice(listOrdersWerePaidInformations);
                    for (Map.Entry<String, BigDecimal> entry : totalPriceAndQuantity.entrySet()) {
                        String key = entry.getKey();
                        BigDecimal value = entry.getValue();
                        if (key.equalsIgnoreCase(TOTAL_PRICE)) {
                            doanhThuTheoNhanVien.setTotalPrice(value);
                        } else if (key.equalsIgnoreCase(QUANTITY)) {
                            doanhThuTheoNhanVien.setTotalOrderSuccess(value.intValue());
                        }
                    }
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
        BigDecimal quantity = BigDecimal.valueOf((long) listOrdersWerePaidInformations.size());
        for (OrdersWerePaidInformation temp : listOrdersWerePaidInformations) {
            totalPrice = totalPrice.add(temp.getTotalPrice());
        }
        result.put(TOTAL_PRICE, totalPrice);
        result.put(QUANTITY, quantity);
        return result;
    }
}
