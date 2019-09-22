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

    /**
     * Get Overview Revenue: Doanh Thu Tổng Quan
     *
     * @param restaurantId
     * @return
     */
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

    /**
     * Get Overview Revenue in period time
     * Given 2 date, retrieve the revenue report
     *
     * @param request
     * @return
     */
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

    /**
     * Get Revenue Category: Doanh thu theo Danh Mục
     *
     * @param restaurantId
     * @param currentDate
     * @return
     */
    public RevenueCategoryResponse getRevenueCategory(String restaurantId, String currentDate) {
        RevenueCategoryResponse response = new RevenueCategoryResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_REVENUE_CATEGORY_REPORT_FAIL);
        try {
            if (StringUtils.isNotEmpty(restaurantId)) {
                String theDayBefore = DateUtils.getDayBeforeTheGivenDay(currentDate);
                List<Order> listOrdersCurrentDay = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, restaurantId);
                List<Order> listOrdersTheDayBefore = orderRepository.findAllOrderByCreatedDateAndRestaurantId(theDayBefore, restaurantId);
                List<HotDealsCategory> listHotDealsCategoryCurrentDate = new ArrayList<HotDealsCategory>();
                List<HotDealsCategory> listHotDealsCategoryTheDayBefore = new ArrayList<HotDealsCategory>();
                if (CollectionUtils.isNotEmpty(listOrdersCurrentDay)) {
                    listHotDealsCategoryCurrentDate = analysisRevenueCategory(listOrdersCurrentDay);
                }
                if (CollectionUtils.isNotEmpty(listOrdersTheDayBefore)) {
                    listHotDealsCategoryTheDayBefore = analysisRevenueCategory(listOrdersTheDayBefore);
                }
                List<HotDealsCategory> result = calculateRevenuePercentageOfRevenueCategory(listHotDealsCategoryCurrentDate,
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

    /**
     * Given the period time, analysis the revenue category
     *
     * @param request
     * @return
     */
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

    private List<HotDealsCategory> calculateRevenuePercentageOfRevenueCategory(List<HotDealsCategory> ordersCurrentDay,
                                                                               List<HotDealsCategory> ordersTheDayBefore) {
        List<HotDealsCategory> result = new ArrayList<HotDealsCategory>();
        if (ordersCurrentDay.size() > 0 && ordersTheDayBefore.size() > 0) {
            for (HotDealsCategory hotDealsCategoryCurrentDate : ordersCurrentDay) {
                boolean isFounded = false;
                for (HotDealsCategory hotDealsCategoryTheDayBefore : ordersTheDayBefore) {
                    if (hotDealsCategoryTheDayBefore.getCategoryName().equalsIgnoreCase(hotDealsCategoryCurrentDate.getCategoryName())) {
                        isFounded = true;
                        HotDealsCategory temp = new HotDealsCategory();
                        temp.setCategoryName(hotDealsCategoryCurrentDate.getCategoryName());
                        temp.setTotalQuantity(hotDealsCategoryCurrentDate.getTotalQuantity());
                        temp.setTotalPrice(hotDealsCategoryCurrentDate.getTotalPrice());
                        BigDecimal revenuePercentage = ((hotDealsCategoryCurrentDate.getTotalPrice().
                                subtract(hotDealsCategoryTheDayBefore.getTotalPrice())).
                                multiply(BigDecimal.valueOf(100))).divide(hotDealsCategoryTheDayBefore.getTotalPrice(),
                                2, RoundingMode.HALF_UP);
                        temp.setRevenuePercentage(revenuePercentage + "%");
                        if (revenuePercentage.compareTo(BigDecimal.ZERO) > 0) {
                            temp.setStatus(PercentageRevenueStatusType.INCREASE);
                        } else {
                            temp.setStatus(PercentageRevenueStatusType.DECREASE);
                        }
                        List<HotDealsProduct> listHotDealsProduct = calculateRevenuePercentageOfHotDealsProducts(hotDealsCategoryCurrentDate.getHotDealsProducts(),
                                hotDealsCategoryTheDayBefore.getHotDealsProducts());
                        temp.setHotDealsProducts(listHotDealsProduct);
                        result.add(temp);
                        break;
                    }
                }
                if (!isFounded) {
                    HotDealsCategory temp = new HotDealsCategory();
                    temp.setCategoryName(hotDealsCategoryCurrentDate.getCategoryName());
                    temp.setTotalQuantity(hotDealsCategoryCurrentDate.getTotalQuantity());
                    temp.setStatus(PercentageRevenueStatusType.INCREASE_INFINITY);
                    temp.setTotalPrice(hotDealsCategoryCurrentDate.getTotalPrice());
                    for (HotDealsProduct hotDealsProduct : hotDealsCategoryCurrentDate.getHotDealsProducts()) {
                        hotDealsProduct.setStatus(PercentageRevenueStatusType.INCREASE_INFINITY);
                    }
                    temp.setHotDealsProducts(hotDealsCategoryCurrentDate.getHotDealsProducts());
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

    /**
     * Hot Deals Products Report: Báo Cáo Mặt Hàng Bán Chạy
     *
     * @param cuaHangId
     * @param currentDate
     * @return
     */
    public HotDealsProductsResponse getHotDealsProducts(String cuaHangId, String currentDate) {
        HotDealsProductsResponse response = new HotDealsProductsResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_HOT_DEALS_PRODUCTS_FAIL);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                String theDayBefore = DateUtils.getDayBeforeTheGivenDay(currentDate);
                List<Order> listOrdersCurrentDate = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, cuaHangId);
                List<Order> listOrdersTheDayBefore = orderRepository.findAllOrderByCreatedDateAndRestaurantId(theDayBefore, cuaHangId);
                List<HotDealsProduct> listHotDealsProductCurrentDay = new ArrayList<HotDealsProduct>();
                List<HotDealsProduct> listHotDealsProductTheDayBefore = new ArrayList<HotDealsProduct>();
                if (CollectionUtils.isNotEmpty(listOrdersCurrentDate)) {
                    listHotDealsProductCurrentDay = analysisHotDealsProducts(listOrdersCurrentDate);
                }
                if (CollectionUtils.isNotEmpty(listOrdersTheDayBefore)) {
                    listHotDealsProductTheDayBefore = analysisHotDealsProducts(listOrdersTheDayBefore);
                }
                List<HotDealsProduct> result = calculateRevenuePercentageOfHotDealsProducts(listHotDealsProductCurrentDay, listHotDealsProductTheDayBefore);
                if (!result.isEmpty()) {
                    response.setRestaurantId(cuaHangId);
                    response.setHotDealsProductList(result);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when get hot deals products: {}", e);
        }
        return response;
    }

    /**
     * Given the period time, analysis the hot deals products report
     *
     * @param request
     * @return
     */
    public HotDealsProductsResponse getHotDealsProductsInRangeDate(AbstractReportRequest request) {
        HotDealsProductsResponse response = new HotDealsProductsResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_HOT_DEALS_PRODUCTS_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setRestaurantId(request.getRestaurantId());
                    List<HotDealsProduct> hotDealsProductList = analysisHotDealsProducts(orders);
                    response.setHotDealsProductList(hotDealsProductList);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when get hot deals products in the period time: {}", e);
        }
        return response;
    }

    private List<HotDealsProduct> analysisHotDealsProducts(List<Order> orders) {
        List<HotDealsProduct> listHotDealsProduct = new ArrayList<HotDealsProduct>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                List<ProductConsumeObject> productConsumeObjectList = order.getProductConsumeList();
                for (ProductConsumeObject productConsumeObject : productConsumeObjectList) {
                    int quantity = productConsumeObject.getQuantity();
                    BigDecimal price = productConsumeObject.getUnitPrice().multiply(BigDecimal.valueOf((double) productConsumeObject.getQuantity()));
                    String productName = productConsumeObject.getProductName() + " (" + productConsumeObject.getPriceName() + ") ";
                    if (checkHotDealsProductExist(listHotDealsProduct, productName)) {
                        int productIndex = IntStream.range(0, listHotDealsProduct.size()).filter(i ->
                                productName.equalsIgnoreCase(listHotDealsProduct.get(i).getName())).findFirst().getAsInt();
                        int originalQuantity = listHotDealsProduct.get(productIndex).getQuantity();
                        listHotDealsProduct.get(productIndex).setQuantity(quantity + originalQuantity);
                        BigDecimal originalPrice = listHotDealsProduct.get(productIndex).getTotalPrice();
                        listHotDealsProduct.get(productIndex).setTotalPrice(price.add(originalPrice));
                    } else {
                        HotDealsProduct hotDealsProduct = new HotDealsProduct();
                        hotDealsProduct.setQuantity(quantity);
                        hotDealsProduct.setTotalPrice(price);
                        hotDealsProduct.setName(productName);
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

    private List<HotDealsProduct> calculateRevenuePercentageOfHotDealsProducts(List<HotDealsProduct> listCurrentDay, List<HotDealsProduct> listTheDayBefore) {
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

    /**
     * Discount Report: Báo Cáo Giảm Giá
     *
     * @param restaurantId
     * @param currentDate
     * @return
     */
    public DiscountReportResponse getDiscountReport(String restaurantId, String currentDate) {
        DiscountReportResponse response = new DiscountReportResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_DISCOUNT_REPORT_FAIL);
        try {
            if (StringUtils.isNotEmpty(restaurantId)) {
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, restaurantId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setRestaurantId(restaurantId);
                    List<DiscountReport> discountReportList = analysisDiscountReport(orders);
                    response.setDiscountReportList(discountReportList);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when getBaoCaoGiamGia: {}", e);
        }
        return response;
    }

    /**
     * Given the period time, analysis the discount report
     *
     * @param request
     * @return
     */
    public DiscountReportResponse getDiscountReportInRangeDate(AbstractReportRequest request) {
        DiscountReportResponse response = new DiscountReportResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_DISCOUNT_REPORT_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(),
                        fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setRestaurantId(request.getRestaurantId());
                    List<DiscountReport> discountReportList = analysisDiscountReport(orders);
                    response.setDiscountReportList(discountReportList);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when getBaoCaoGiamGiaInRangeDate: {}", e);
        }
        return response;
    }

    private List<DiscountReport> analysisDiscountReport(List<Order> orders) {
        List<DiscountReport> listDiscountReport = new ArrayList<DiscountReport>();
        for (Order order : orders) {
            if (order.getOrderStatus().equals(OrderStatus.SUCCESS)) {
                DiscountReport discountReport = new DiscountReport();
                if (!order.getDiscountType().equals(DiscountType.NONE)) {
                    String discountName = order.getDiscountName();
                    String discountReportName = "";
                    if (order.getDiscountType().label.equalsIgnoreCase(DiscountType.GIẢM_GIÁ_THEO_DANH_MỤC.label)) {
                        String percent = order.getPercentageDiscount() + "%";
                        discountReportName = discountName + " (-" + percent + ")";
                    } else if (order.getDiscountType().label.equalsIgnoreCase(DiscountType.GIẢM_GIÁ_THÔNG_THƯỜNG.label)) {
                        if (order.getGiamGiaDiscountAmount() != null) {
                            String discountAmount = order.getGiamGiaDiscountAmount() + "đ";
                            discountReportName = discountName + " (-" + discountAmount + ")";
                        } else if (order.getPercentageDiscount() != null) {
                            String percent = order.getPercentageDiscount() + "%";
                            discountReportName = discountName + " (-" + percent + ")";
                        }
                    }
                    if (checkDiscountReportNameExist(listDiscountReport, discountReportName)) {
                        String finalDiscountReportNameName = discountReportName;
                        int index = IntStream.range(0, listDiscountReport.size()).filter(i ->
                                finalDiscountReportNameName.equalsIgnoreCase(listDiscountReport.get(i).getName())).findFirst().getAsInt();
                        listDiscountReport.get(index).setTotalQuantity(listDiscountReport.get(index).getTotalQuantity() + 1);
                        List<BaoCaoGiamGiaDetail> giamGiaDetails = listDiscountReport.get(index).getListDiscountInvoice();
                        BaoCaoGiamGiaDetail temp = new BaoCaoGiamGiaDetail();
                        temp.setDiscountPrice(order.getDiscountMoney());
                        temp.setReceiptCode(order.getReceiptCode());
                        temp.setTime(order.getOrderTimeCheckOut());
                        giamGiaDetails.add(temp);
                        listDiscountReport.get(index).setListDiscountInvoice(giamGiaDetails);
                        BigDecimal totalDiscount = calculateDiscountMoney(giamGiaDetails);
                        listDiscountReport.get(index).setTotalDiscount(totalDiscount);
                    } else {
                        discountReport.setTotalQuantity(1);
                        discountReport.setName(discountReportName);
                        List<BaoCaoGiamGiaDetail> giamGiaDetails = new ArrayList<BaoCaoGiamGiaDetail>();
                        BaoCaoGiamGiaDetail temp = new BaoCaoGiamGiaDetail();
                        temp.setDiscountPrice(order.getDiscountMoney());
                        temp.setReceiptCode(order.getReceiptCode());
                        temp.setTime(order.getOrderTimeCheckOut());
                        giamGiaDetails.add(temp);
                        discountReport.setListDiscountInvoice(giamGiaDetails);
                        listDiscountReport.add(discountReport);
                        BigDecimal totalDiscount = calculateDiscountMoney(giamGiaDetails);
                        discountReport.setTotalDiscount(totalDiscount);
                    }
                }
            }
        }
        return listDiscountReport;
    }

    private boolean checkDiscountReportNameExist(List<DiscountReport> listDiscountReport, String discountReportName) {
        boolean isExisted = false;
        if (listDiscountReport.stream().anyMatch(tmp -> tmp.getName().equalsIgnoreCase(discountReportName))) {
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

    /**
     * Get the employees revenue report
     *
     * @param restaurantId
     * @param currentDate
     * @return
     */
    public EmployeesRevenueResponse getEmployeesRevenueReport(String restaurantId, String currentDate) {
        EmployeesRevenueResponse response = new EmployeesRevenueResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_EMPLOYEES_REVENUE_REPORT_FAIL);
        try {
            if (StringUtils.isNotEmpty(restaurantId)) {
                List<Order> orders = orderRepository.findAllOrderByCreatedDateAndRestaurantId(currentDate, restaurantId);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setRestaurantId(restaurantId);
                    List<EmployeesRevenue> listEmployeesRevenue = analysisEmployeesRevenue(orders);
                    response.setListEmployeeSRevenue(listEmployeesRevenue);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when get the employees revenue report: ", e);
        }
        return response;
    }

    /**
     * Given the period time, analysis the employees revenue report
     *
     * @param request
     * @return
     */
    public EmployeesRevenueResponse getEmployeesRevenueReportInRangeDate(AbstractReportRequest request) {
        EmployeesRevenueResponse response = new EmployeesRevenueResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_EMPLOYEES_REVENUE_REPORT_FAIL);
        try {
            if (request != null) {
                String fromDate = request.getFromDate();
                String toDate = request.getToDate();
                List<Order> orders = orderRepository.findAllOrderByCuaHangIdCreateDateBetween(request.getRestaurantId(), fromDate, toDate);
                if (CollectionUtils.isNotEmpty(orders)) {
                    response.setRestaurantId(request.getRestaurantId());
                    List<EmployeesRevenue> listEmployeesRevenue = analysisEmployeesRevenue(orders);
                    response.setListEmployeeSRevenue(listEmployeesRevenue);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when get the employees revenue report in the period time: ", e);
        }
        return response;
    }

    private List<EmployeesRevenue> analysisEmployeesRevenue(List<Order> orders) {
        List<EmployeesRevenue> listEmployeesRevenue = new ArrayList<EmployeesRevenue>();
        for (Order order : orders) {
            if (order.getOrderStatus().toString().equalsIgnoreCase(OrderStatus.SUCCESS.toString())) {
                if (checkWaiterNameIsExisted(listEmployeesRevenue, order.getWaiterName())) {
                    int index = IntStream.range(0, listEmployeesRevenue.size()).filter(i ->
                            order.getWaiterName().equalsIgnoreCase(listEmployeesRevenue.get(i).getEmployeeName())).findFirst().getAsInt();
                    OrdersWerePaidInformation orderPaidInformation = new OrdersWerePaidInformation();
                    orderPaidInformation.setReceiptCode(order.getReceiptCode());
                    orderPaidInformation.setTime(order.getOrderTimeCheckOut());
                    orderPaidInformation.setTotalPrice(order.getTotalMoney());
                    listEmployeesRevenue.get(index).getListOrdersWerePaidInfor().add(orderPaidInformation);
                    Map<String, BigDecimal> totalPriceAndQuantity = calculateEmployeesRevenueTotalPrice(listEmployeesRevenue.get(index).getListOrdersWerePaidInfor());
                    listEmployeesRevenue.get(index).setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                    listEmployeesRevenue.get(index).setTotalOrderSuccess(totalPriceAndQuantity.get(QUANTITY).intValue());
                } else {
                    EmployeesRevenue employeesRevenue = new EmployeesRevenue();
                    employeesRevenue.setEmployeeName(order.getWaiterName());
                    List<OrdersWerePaidInformation> listOrdersWerePaidInformations = new ArrayList<OrdersWerePaidInformation>();
                    OrdersWerePaidInformation orderPaidInformation = new OrdersWerePaidInformation();
                    orderPaidInformation.setReceiptCode(order.getReceiptCode());
                    orderPaidInformation.setTime(order.getOrderTimeCheckOut());
                    orderPaidInformation.setTotalPrice(order.getTotalMoney());
                    listOrdersWerePaidInformations.add(orderPaidInformation);
                    employeesRevenue.setListOrdersWerePaidInfor(listOrdersWerePaidInformations);
                    Map<String, BigDecimal> totalPriceAndQuantity = calculateEmployeesRevenueTotalPrice(listOrdersWerePaidInformations);
                    employeesRevenue.setTotalPrice(totalPriceAndQuantity.get(TOTAL_PRICE));
                    employeesRevenue.setTotalOrderSuccess(totalPriceAndQuantity.get(QUANTITY).intValue());
                    listEmployeesRevenue.add(employeesRevenue);
                }

            }
        }
        return listEmployeesRevenue;
    }

    private boolean checkWaiterNameIsExisted(List<EmployeesRevenue> listEmployeesRevenue, String waiterName) {
        boolean isExisted = false;
        if (listEmployeesRevenue.stream().anyMatch(tmp -> tmp.getEmployeeName().equalsIgnoreCase(waiterName))) {
            isExisted = true;
        }
        return isExisted;
    }

    private Map<String, BigDecimal> calculateEmployeesRevenueTotalPrice(List<OrdersWerePaidInformation> listOrdersWerePaidInformations) {
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
