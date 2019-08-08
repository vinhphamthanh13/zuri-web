package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.entity.DanhMucBanChay;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DoanhThuTheoDanhMucResponse extends AbstractResponse {

    List<DanhMucBanChay> listDanhMucBanChay;

    private String cuaHangId;
}
