package com.ocha.boc.entity;

import com.ocha.boc.enums.GiamGiaType;
import com.ocha.boc.enums.OrderStatus;
import com.ocha.boc.enums.OrderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = Order.COLLECTION_NAME)
public class Order {

    public static final String COLLECTION_NAME = "order";

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

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

    private List<MatHangTieuThu> listMatHangTieuThu = new ArrayList<MatHangTieuThu>();

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

    /**
     * Hoàn Tiền: Only if status order is CANCEL
     */
    private BigDecimal refunds;

    /**
     * Discount Money: Tiền được giảm.
     */
    private BigDecimal discountMoney;

    private GiamGiaType giamGiaType;

    private String giamGiaName;

    private BigDecimal giamGiaPercentage;

    private BigDecimal giamGiaDiscountAmount;

    /**
     * danhMucIsDiscountedId: danh mục được giảm giá với giảm giá type là "GIẢM GIÁ THEO DANH MỤC"
     */
    private String danhMucIsDiscountedId;

}
