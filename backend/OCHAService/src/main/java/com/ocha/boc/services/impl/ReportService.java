package com.ocha.boc.services.impl;

import com.ocha.boc.entity.*;
import com.ocha.boc.enums.DiscountType;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.enums.PercentageRevenueStatusType;
import com.ocha.boc.repository.CategoryRepository;
import com.ocha.boc.repository.OrderRepository;
import com.ocha.boc.request.AbstractReportRequest;
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
public class ReportService {

    private static final String TOTAL_PRICE = "TOTAL";
    private static final String QUANTITY = "QUANTITY";
    private static final int ZERO_NUMBER = 0;
    private static final String ONE_HUNDRED_PERCENT = "100%";

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public OverviewRevenueResponse getOverviewRevenue(String restaurantId) {
        OverviewRevenueResponse response = new OverviewRevenueResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_OVERVIEW_REVENUE_FAIL);
        try {
            if (StringUtils.isNotEmpty(restaurantId)) {
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndRestaurantId(DateUtils.getCurrentDate(),
                        restaurantId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setRestaurantId(restaurantId);
                    analysisOverviewRevenue(orders, response);
                }
            }
        } catch (Exception e) {
            log.error("Error when get overview revenue report: ", e);
        }
        return response;
    }

    public OverviewRevenueResponse getOverviewRevenueInRangeDate(AbstractReportRequest request) {
        OverviewRevenueResponse response = new OverviewRevenueResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_OVERVIEW_REVENUE_IN_RANGE_DATE_FAIL);
        try {
            if (!Objects.isNull(request)) {
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(),
                                                                            request.getFromDate(), request.getToDate());
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setRestaurantId(request.getRestaurantId());
                    analysisOverviewRevenue(orders, response);
                }
            }
        } catch (Exception e) {
            log.error("Error when getDoanhThuTongQuanInRangeDate: {}", e);
        }
        return response;
    }

    private void analysisOverviewRevenue(List<Order> orders, OverviewRevenueResponse response) {
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

    public RevenueCategoryResponse getRevenueCategory(String restaurantId, String currentDate) {
        RevenueCategoryResponse response = new RevenueCategoryResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_REVENUE_CATEGORY_REPORT_FAIL);
        try {
            if (StringUtils.isNotEmpty(restaurantId)) {
                String theDayBefore = DateUtils.getDayBeforeTheGivenDay(currentDate);
                List<Order> listOrdersCurrentDay = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, restaurantId);
                List<Order> listOrdersTheDayBefore = orderRepository.findAllOrderByCreatedDateAndRestaurantId(theDayBefore, restaurantId);
                List<HotDealsCategory> listHotDealsCategoryCurrentDay = new ArrayList<HotDealsCategory>();
                List<HotDealsCategory> listHotDealsCategoryTheDayBefore = new ArrayList<HotDealsCategory>();
                if (CollectionUtils.isNotEmpty(listOrdersCurrentDay)) {
                    listHotDealsCategoryCurrentDay = analysisRevenueCategory(listOrdersCurrentDay);
                }
                if (CollectionUtils.isNotEmpty(listOrdersTheDayBefore)) {
                    listHotDealsCategoryTheDayBefore = analysisRevenueCategory(listOrdersTheDayBefore);
                }
                List<HotDealsCategory> result = calculateRevenuePercentageOfRevenuceCategory(listHotDealsCategoryCurrentDay,
                        listHotDealsCategoryTheDayBefore);
                if (!result.isEmpty()) {
                    response.setCuaHangId(restaurantId);
                    response.setListHotDealsCategory(result);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when get revenue category report: ", e);
        }
        return response;
    }

    public RevenueCategoryResponse getRevenueCategoryInRangeDate(AbstractReportRequest request) {
        RevenueCategoryResponse response = new RevenueCategoryResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_REVENUE_CATEGORY_REPORT_FAIL);
        if (request != null) {
            String fromDate = request.getFromDate();
            String toDate = request.getToDate();
            List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(), fromDate, toDate);
            if (CollectionUtils.isNotEmpty(orders)) {
                response.setCuaHangId(request.getRestaurantId());
                List<HotDealsCategory> listHotDealsCategory = analysisRevenueCategory(orders);
                response.setListHotDealsCategory(listHotDealsCategory);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            }
        }
        return response;
    }

    private List<HotDealsCategory> analysisRevenueCategory(List<Order> orders) {
        List<HotDealsCategory> hotDealsCategoryList = new ArrayList<HotDealsCategory>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                List<ProductConsumeObject> listProductConsumeObject = order.getProductConsumeList();
                for (ProductConsumeObject temp : listProductConsumeObject) {
                    String productName = temp.getProductName() + " (" + temp.getPriceName() + ") ";
                    Optional<Category> optCategory = categoryRepository.findCategoryByCategoryIdAndRestaurantId(temp.getCategoryId(), order.getRestaurantId());
                    if (optCategory.isPresent()) {
                        //Check danh muc existed List<HotDealsCategory>
                        if (!checkCategoryExistInListHotDealsCategories(hotDealsCategoryList, optCategory.get())) {
                            //Hot Deals Category means: Danh Muc Ban Chay
                            HotDealsCategory hotDealsCategory = new HotDealsCategory();
                            //Hot Deals Product: Mat Hang Ban Chay
                            List<HotDealsProduct> hotDealsProductList = new ArrayList<HotDealsProduct>();
                            HotDealsProduct hotDealsProduct = new HotDealsProduct();
                            hotDealsProduct.setName(productName);
                            hotDealsProduct.setQuantity(temp.getQuantity());
                            hotDealsProduct.setTotalPrice(temp.getUnitPrice().multiply(BigDecimal.valueOf((long) temp.getQuantity())));
                            hotDealsProductList.add(hotDealsProduct);
                            Map<String, BigDecimal> totalPriceAndQuantity = calculateTotalPriceAndQuantityListHotDealsProduct(hotDealsProductList);
                            hotDealsCategory.setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                            hotDealsCategory.setTotalQuantity(totalPriceAndQuantity.get(QUANTITY).intValue());
                            hotDealsCategory.setCategoryName(optCategory.get().getName());
                            hotDealsCategory.setHotDealsProducts(hotDealsProductList);
                            hotDealsCategoryList.add(hotDealsCategory);
                        } else {
                            int index = IntStream.range(0, hotDealsCategoryList.size()).filter(i ->
                                    optCategory.get().getName().equalsIgnoreCase(hotDealsCategoryList.get(i).getCategoryName())).findFirst().getAsInt();
                            List<HotDealsProduct> listHotDealsProduct = hotDealsCategoryList.get(index).getHotDealsProducts();
                            if (checkHotDealsProductExist(listHotDealsProduct, productName)) {
                                int indexProduct = IntStream.range(0, listHotDealsProduct.size()).filter(i ->
                                        productName.equalsIgnoreCase(listHotDealsProduct.get(i).getName())).findFirst().getAsInt();
                                int quantity = listHotDealsProduct.get(indexProduct).getQuantity();
                                listHotDealsProduct.get(indexProduct).setQuantity(quantity + temp.getQuantity());
                                BigDecimal price = listHotDealsProduct.get(indexProduct).getTotalPrice();
                                listHotDealsProduct.get(indexProduct).setTotalPrice(
                                        price.add(temp.getUnitPrice().multiply(BigDecimal.valueOf(temp.getQuantity()))));
                            } else {
                                HotDealsProduct hotDealsProduct = new HotDealsProduct();
                                hotDealsProduct.setName(productName);
                                hotDealsProduct.setQuantity(temp.getQuantity());
                                hotDealsProduct.setTotalPrice(temp.getUnitPrice());
                                listHotDealsProduct.add(hotDealsProduct);
                            }
                            Map<String, BigDecimal> totalPriceAndQuantity = calculateTotalPriceAndQuantityListHotDealsProduct(listHotDealsProduct);
                            hotDealsCategoryList.get(index).setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                            hotDealsCategoryList.get(index).setTotalQuantity(totalPriceAndQuantity.get(QUANTITY).intValue());
                        }
                    }
                }
            }
        }
        return hotDealsCategoryList;
    }

    private boolean checkCategoryExistInListHotDealsCategories(List<HotDealsCategory> listHotDealsCategory, Category category) {
        boolean isExisted = false;
        if (listHotDealsCategory.stream().anyMatch(tmp -> tmp.getCategoryName().equalsIgnoreCase(category.getName()))) {
            isExisted = true;
        }
        return isExisted;
    }

    private Map<String, BigDecimal> calculateTotalPriceAndQuantityListHotDealsProduct(List<HotDealsProduct> listHotDealsProduct) {
        Map<String, BigDecimal> result = new HashMap<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        int quantity = 0;
        for (HotDealsProduct hotDealsProduct : listHotDealsProduct) {
            totalPrice = totalPrice.add(hotDealsProduct.getTotalPrice());
            quantity += hotDealsProduct.getQuantity();
        }
        result.put(TOTAL_PRICE, totalPrice);
        result.put(QUANTITY, BigDecimal.valueOf((double) quantity));
        return result;
    }

    private List<HotDealsCategory> calculateRevenuePercentageOfRevenuceCategory(List<HotDealsCategory> ordersCurrentDay,
                                                                                List<HotDealsCategory> ordersTheDayBefore) {
        List<HotDealsCategory> result = new ArrayList<HotDealsCategory>();
        if (ordersCurrentDay.size() > 0 && ordersTheDayBefore.size() > 0) {
            for (HotDealsCategory hotDealsCategoryCurrenDay : ordersCurrentDay) {
                boolean isFounded = false;
                for (HotDealsCategory hotDealsCategoryTheDayBefore : ordersTheDayBefore) {
                    if (hotDealsCategoryTheDayBefore.getCategoryName().equalsIgnoreCase(hotDealsCategoryCurrenDay.getCategoryName())) {
                        isFounded = true;
                        HotDealsCategory temp = new HotDealsCategory();
                        temp.setCategoryName(hotDealsCategoryCurrenDay.getCategoryName());
                        temp.setTotalQuantity(hotDealsCategoryCurrenDay.getTotalQuantity());
                        temp.setTotalPrice(hotDealsCategoryCurrenDay.getTotalPrice());
                        BigDecimal revenuePercentage = ((hotDealsCategoryCurrenDay.getTotalPrice().
                                subtract(hotDealsCategoryTheDayBefore.getTotalPrice())).
                                multiply(BigDecimal.valueOf(100))).divide(hotDealsCategoryTheDayBefore.getTotalPrice(),
                                2, RoundingMode.HALF_UP);
                        temp.setRevenuePercentage(revenuePercentage + "%");
                        if (revenuePercentage.compareTo(BigDecimal.ZERO) > 0) {
                            temp.setStatus(PercentageRevenueStatusType.INCREASE);
                        } else {
                            temp.setStatus(PercentageRevenueStatusType.DECREASE);
                        }
                        List<HotDealsProduct> listHotDealsProduct = calculateRevenuePercentageOfHotSellingProduct(hotDealsCategoryCurrenDay.getHotDealsProducts(),
                                hotDealsCategoryTheDayBefore.getHotDealsProducts());
                        temp.setHotDealsProducts(listHotDealsProduct);
                        result.add(temp);
                        break;
                    }
                }
                if (!isFounded) {
                    HotDealsCategory temp = new HotDealsCategory();
                    temp.setCategoryName(hotDealsCategoryCurrenDay.getCategoryName());
                    temp.setTotalQuantity(hotDealsCategoryCurrenDay.getTotalQuantity());
                    temp.setStatus(PercentageRevenueStatusType.INCREASE_INFINITY);
                    temp.setTotalPrice(hotDealsCategoryCurrenDay.getTotalPrice());
                    for (HotDealsProduct hotDealsProduct : hotDealsCategoryCurrenDay.getHotDealsProducts()) {
                        hotDealsProduct.setStatus(PercentageRevenueStatusType.INCREASE_INFINITY);
                    }
                    temp.setHotDealsProducts(hotDealsCategoryCurrenDay.getHotDealsProducts());
                    result.add(temp);
                }
            }
        } else if (ordersCurrentDay.size() == 0 && ordersTheDayBefore.size() > 0) {
            for (HotDealsCategory hotDealsCategory : ordersTheDayBefore) {
                hotDealsCategory.setTotalPrice(BigDecimal.ZERO);
                hotDealsCategory.setTotalQuantity(ZERO_NUMBER);
                hotDealsCategory.setHotDealsProducts(new ArrayList<HotDealsProduct>());
                hotDealsCategory.setStatus(PercentageRevenueStatusType.DECREASE);
                hotDealsCategory.setRevenuePercentage(ONE_HUNDRED_PERCENT);
            }
            result = ordersTheDayBefore;
        } else if (ordersCurrentDay.size() > 0 && ordersTheDayBefore.size() == 0) {
            for (HotDealsCategory hotDealsCategory : ordersCurrentDay) {
                hotDealsCategory.setStatus(PercentageRevenueStatusType.INCREASE_INFINITY);
            }
            result = ordersCurrentDay;
        }
        for (HotDealsCategory hotDealsCategoryTheDayBefore : ordersTheDayBefore) {
            boolean isFounded = false;
            for (HotDealsCategory hotDealsCategoryInResult : result) {
                if (hotDealsCategoryInResult.getCategoryName().equalsIgnoreCase(hotDealsCategoryTheDayBefore.getCategoryName())) {
                    isFounded = true;
                    break;
                }
            }
            if (!isFounded) {
                HotDealsCategory temp = new HotDealsCategory();
                temp.setCategoryName(hotDealsCategoryTheDayBefore.getCategoryName());
                temp.setTotalQuantity(ZERO_NUMBER);
                temp.setStatus(PercentageRevenueStatusType.DECREASE);
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
                List<Order> listOrdersCurrentDay = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, cuaHangId);
                List<Order> listOrdersTheDayBefore = orderRepository.findAllOrderByCreatedDateAndRestaurantId(theDayBefore, cuaHangId);
                List<HotDealsProduct> listHotDealsProductCurrentDay = new ArrayList<HotDealsProduct>();
                List<HotDealsProduct> listHotDealsProductTheDayBefore = new ArrayList<HotDealsProduct>();
                if (CollectionUtils.isNotEmpty(listOrdersCurrentDay)) {
                    listHotDealsProductCurrentDay = analysisMatHangBanChay(listOrdersCurrentDay);
                }
                if (CollectionUtils.isNotEmpty(listOrdersTheDayBefore)) {
                    listHotDealsProductTheDayBefore = analysisMatHangBanChay(listOrdersTheDayBefore);
                }
                List<HotDealsProduct> result = calculateRevenuePercentageOfHotSellingProduct(listHotDealsProductCurrentDay, listHotDealsProductTheDayBefore);
                if (!result.isEmpty()) {
                    response.setCuaHangId(cuaHangId);
                    response.setHotDealsProductList(result);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when getMatHangBanChay: {}", e);
        }
        return response;
    }

    public MatHangBanChayResponse getMatHangBanChayInRangeDate(AbstractReportRequest request) {
        MatHangBanChayResponse response = new MatHangBanChayResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_MAT_HANG_BAN_CHAY_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(request.getRestaurantId());
                    List<HotDealsProduct> hotDealsProductList = analysisMatHangBanChay(orders);
                    response.setHotDealsProductList(hotDealsProductList);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when getMatHangBanChayInRangeDate: {}", e);
        }
        return response;
    }

    private List<HotDealsProduct> analysisMatHangBanChay(List<Order> orders) {
        List<HotDealsProduct> listHotDealsProduct = new ArrayList<HotDealsProduct>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                List<ProductConsumeObject> productConsumeObjectList = order.getProductConsumeList();
                for (ProductConsumeObject productConsumeObject : productConsumeObjectList) {
                    int quantity = productConsumeObject.getQuantity();
                    BigDecimal price = productConsumeObject.getUnitPrice().multiply(BigDecimal.valueOf((double) productConsumeObject.getQuantity()));
                    String matHangName = productConsumeObject.getProductName() + " (" + productConsumeObject.getPriceName() + ") ";
                    if (checkHotDealsProductExist(listHotDealsProduct, matHangName)) {
                        int indexMatHang = IntStream.range(0, listHotDealsProduct.size()).filter(i ->
                                matHangName.equalsIgnoreCase(listHotDealsProduct.get(i).getName())).findFirst().getAsInt();
                        int originalQuantity = listHotDealsProduct.get(indexMatHang).getQuantity();
                        listHotDealsProduct.get(indexMatHang).setQuantity(quantity + originalQuantity);
                        BigDecimal originalPrice = listHotDealsProduct.get(indexMatHang).getTotalPrice();
                        listHotDealsProduct.get(indexMatHang).setTotalPrice(price.add(originalPrice));
                    } else {
                        HotDealsProduct hotDealsProduct = new HotDealsProduct();
                        hotDealsProduct.setQuantity(quantity);
                        hotDealsProduct.setTotalPrice(price);
                        hotDealsProduct.setName(matHangName);
                        listHotDealsProduct.add(hotDealsProduct);
                    }
                }
            }
        }
        return listHotDealsProduct;
    }

    private boolean checkHotDealsProductExist(List<HotDealsProduct> listHotDealsProduct, String productName) {
        boolean isExisted = false;
        if (listHotDealsProduct.stream().anyMatch(tmp -> tmp.getName().equalsIgnoreCase(productName))) {
            isExisted = true;
        }
        return isExisted;
    }

    private List<HotDealsProduct> calculateRevenuePercentageOfHotSellingProduct(List<HotDealsProduct> listCurrentDay, List<HotDealsProduct> listTheDayBefore) {
        List<HotDealsProduct> result = new ArrayList<HotDealsProduct>();
        if (listCurrentDay.size() > 0 && listTheDayBefore.size() > 0) {
            for (HotDealsProduct hotDealsProductCurrentDay : listCurrentDay) {
                boolean isFounded = false;
                for (HotDealsProduct hotDealsProductTheDayBefore : listTheDayBefore) {
                    if (hotDealsProductTheDayBefore.getName().equalsIgnoreCase(hotDealsProductCurrentDay.getName())) {
                        isFounded = true;
                        HotDealsProduct temp = new HotDealsProduct();
                        temp.setName(hotDealsProductCurrentDay.getName());
                        temp.setQuantity(hotDealsProductCurrentDay.getQuantity());
                        temp.setTotalPrice(hotDealsProductCurrentDay.getTotalPrice());
                        BigDecimal theDayBeforePrice = hotDealsProductTheDayBefore.getTotalPrice();
                        BigDecimal revenuePercentage = ((hotDealsProductCurrentDay.getTotalPrice().subtract(theDayBeforePrice)).multiply(BigDecimal.valueOf(100))).divide(theDayBeforePrice, 2, RoundingMode.HALF_UP);
                        temp.setRevenuePercentage(revenuePercentage + "%");
                        if (revenuePercentage.compareTo(BigDecimal.ZERO) > 0) {
                            temp.setStatus(PercentageRevenueStatusType.INCREASE);
                        } else if (revenuePercentage.compareTo(BigDecimal.ZERO) == 0) {
                            temp.setStatus(PercentageRevenueStatusType.NORMAL);
                        } else {
                            temp.setStatus(PercentageRevenueStatusType.DECREASE);
                        }
                        result.add(temp);
                    }
                }
                if (!isFounded) {
                    HotDealsProduct hotDealsProduct = new HotDealsProduct();
                    hotDealsProduct.setName(hotDealsProductCurrentDay.getName());
                    hotDealsProduct.setTotalPrice(hotDealsProductCurrentDay.getTotalPrice());
                    hotDealsProduct.setQuantity(hotDealsProductCurrentDay.getQuantity());
                    hotDealsProduct.setStatus(PercentageRevenueStatusType.INCREASE_INFINITY);
                    result.add(hotDealsProduct);
                }
            }
        } else if (listCurrentDay.size() == 0 && listTheDayBefore.size() > 0) {
            for (HotDealsProduct hotDealsProduct : listTheDayBefore) {
                hotDealsProduct.setTotalPrice(BigDecimal.ZERO);
                hotDealsProduct.setQuantity(ZERO_NUMBER);
                hotDealsProduct.setStatus(PercentageRevenueStatusType.DECREASE);
                hotDealsProduct.setRevenuePercentage(ONE_HUNDRED_PERCENT);
            }
            result = listTheDayBefore;
        } else if (listCurrentDay.size() > 0 && listTheDayBefore.size() == 0) {
            for (HotDealsProduct hotDealsProduct : listCurrentDay) {
                hotDealsProduct.setStatus(PercentageRevenueStatusType.INCREASE_INFINITY);
            }
            result = listCurrentDay;
        }
        for (HotDealsProduct hotDealsProductTheDayBefore : listTheDayBefore) {
            boolean isFounded = false;
            for (HotDealsProduct hotDealsProductInResult : result) {
                if (hotDealsProductInResult.getName().equalsIgnoreCase(hotDealsProductTheDayBefore.getName())) {
                    isFounded = true;
                    break;
                }
            }
            if (!isFounded) {
                HotDealsProduct hotDealsProduct = new HotDealsProduct();
                hotDealsProduct.setName(hotDealsProductTheDayBefore.getName());
                hotDealsProduct.setTotalPrice(BigDecimal.ZERO);
                hotDealsProduct.setQuantity(ZERO_NUMBER);
                hotDealsProduct.setStatus(PercentageRevenueStatusType.DECREASE);
                hotDealsProduct.setRevenuePercentage(ONE_HUNDRED_PERCENT);
                result.add(hotDealsProduct);
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
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, cuaHangId);
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

    public BaoCaoGiamGiaResponse getBaoCaoGiamGiaInRangeDate(AbstractReportRequest request) {
        BaoCaoGiamGiaResponse response = new BaoCaoGiamGiaResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_GIAM_GIA_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(request.getRestaurantId());
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
                if (!order.getDiscountType().equals(DiscountType.NONE)) {
                    String giamGiaName = order.getDiscountName();
                    String baoCaoGiamGiaName = "";
                    if (order.getDiscountType().label.equalsIgnoreCase(DiscountType.GIẢM_GIÁ_THEO_DANH_MỤC.label)) {
                        String percent = order.getPercentageDiscount() + "%";
                        baoCaoGiamGiaName = giamGiaName + " (-" + percent + ")";
                    } else if (order.getDiscountType().label.equalsIgnoreCase(DiscountType.GIẢM_GIÁ_THÔNG_THƯỜNG.label)) {
                        if (order.getGiamGiaDiscountAmount() != null) {
                            String discountAmount = order.getGiamGiaDiscountAmount() + "đ";
                            baoCaoGiamGiaName = giamGiaName + " (-" + discountAmount + ")";
                        } else if (order.getPercentageDiscount() != null) {
                            String percent = order.getPercentageDiscount() + "%";
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
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, cuaHangId);
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

    public DoanhThuTheoNhanVienResponse getBaoCaoDoanhThuTheoNhanVienInRangeDate(AbstractReportRequest request) {
        DoanhThuTheoNhanVienResponse response = new DoanhThuTheoNhanVienResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_BAO_CAO_DOANH_THU_THEO_NHAN_VIEN_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setCuaHangId(request.getRestaurantId());
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
