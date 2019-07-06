package com.ocha.boc.dto;

import com.ocha.boc.entity.MatHangTieuThu;
import com.ocha.boc.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO {
    private BigDecimal total;

    private String createDate;

    private String id;

    private BigDecimal sumOfKhuyenMaiPrice;

    private List<MatHangTieuThu> listMatHangTieuThu;


    public OrderDTO(Order order) {
        this.id = order.getId();
        this.createDate = order.getCreateDate();
        this.total = order.getTotal();
        this.sumOfKhuyenMaiPrice = order.getSumOfKhuyenMaiPrice();
        this.listMatHangTieuThu = order.getListMatHangTieuThu();
    }
}
