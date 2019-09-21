package com.ocha.boc.request;

import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCheckoutObjectRequest implements Serializable {

    @NotNull
    private String orderId;

    @NotNull
    private String cuaHangId;

    @NotNull
    private List<MatHangTieuThu> listMatHangTieuThu;

    @NotNull
    private BigDecimal cash;

    private BigDecimal tips;

    private DiscountType discountType;

    private String giamGiaName;

    private BigDecimal giamGiaPercentage;

    private BigDecimal giamGiaDiscountAmount;

    private String danhMucIsDiscountedId;
}
