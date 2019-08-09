package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.entity.MatHangBanChay;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MatHangBanChayResponse extends AbstractResponse {

    private String cuaHangId;

    List<MatHangBanChay> matHangBanChayList;
}
