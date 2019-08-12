package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.entity.DoanhThuTheoNhanVien;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class DoanhThuTheoNhanVienResponse extends AbstractResponse implements Serializable {

    private String cuaHangId;

    private List<DoanhThuTheoNhanVien> listEmployeeSRevenue;

}
