package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.entity.Order;
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

    private String cuaHangId;

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

    private List<MatHangTieuThu> listMatHangTieuThu = new ArrayList<MatHangTieuThu>();

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.cuaHangId = order.getCuaHangId();
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
        this.createdDate = order.getCreatedDate();
        this.lastModifiedDate = order.getLastModifiedDate();
        this.listMatHangTieuThu = order.getListMatHangTieuThu();
    }
}
