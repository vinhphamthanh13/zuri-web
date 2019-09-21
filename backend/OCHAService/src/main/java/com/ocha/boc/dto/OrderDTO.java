package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.ProductConsumeObject;
import com.ocha.boc.entity.Order;
import com.ocha.boc.enums.DiscountType;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.enums.OrderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO extends AbstractEntity {

    private String restaurantId;

    /**
     * Mã Biên Lai
     */
    private String receiptCode;

    private OrderStatus orderStatus;

    private OrderType orderType;

    /**
     * Tên phục vụ
     */
    private String waiterName;

    /**
     * Giờ vào
     */
    private String orderTime;

    /**
     * Giờ ra
     */
    private String orderTimeCheckOut;

    /**
     * Used only orderType is "DÙNG_TẠI_BÀN"
     */
    private String orderLocation;

    /**
     * Used only orderType is "MANG_ĐI".
     */
    private String takeAWayOptionCode;

    private BigDecimal totalMoney;

    /**
     * Tiền mặt
     */
    private BigDecimal cash;

    /**
     * Tiền tip
     */
    private BigDecimal tips = BigDecimal.ZERO;

    /**
     * Tiền thừa
     */
    private BigDecimal excessCash = BigDecimal.ZERO;

    private BigDecimal refunds;

    private List<ProductConsumeObject> ProductConsumeList = new ArrayList<ProductConsumeObject>();

    private BigDecimal discountMoney;

    private DiscountType discountType;

    private String discountName;

    private BigDecimal percentageDiscount;

    private BigDecimal giamGiadiscountAmount;

    /**
     * danhMucIsDiscountedId: danh mục được giảm giá với giảm giá type là "GIẢM GIÁ THEO DANH MỤC"
     */
    private String danhMucIsDiscountedId;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.restaurantId = order.getRestaurantId();
        this.receiptCode = order.getReceiptCode();
        this.orderStatus = order.getOrderStatus();
        this.orderType = order.getOrderType();
        this.waiterName = order.getWaiterName();
        this.orderTime = order.getOrderTime();
        this.orderTimeCheckOut = order.getOrderTimeCheckOut();
        this.orderLocation = order.getOrderLocation();
        this.takeAWayOptionCode = order.getTakeAWayOptionCode();
        this.totalMoney = order.getTotalMoney();
        this.cash = order.getCash();
        this.tips = order.getTips();
        this.excessCash = order.getExcessCash();
        this.refunds = order.getRefunds();
        this.createdDate = order.getCreatedDate();
        this.lastModifiedDate = order.getLastModifiedDate();
        this.ProductConsumeList = order.getProductConsumeList();
        this.discountMoney = order.getDiscountMoney();
        this.discountName = order.getDiscountName();
        this.discountType = order.getDiscountType();
        this.percentageDiscount = order.getPercentageDiscount();
        this.giamGiadiscountAmount = order.getGiamGiaDiscountAmount();
    }
}
