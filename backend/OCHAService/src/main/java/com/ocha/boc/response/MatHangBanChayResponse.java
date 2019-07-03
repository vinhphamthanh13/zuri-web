package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.MathangDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class MatHangBanChayResponse extends AbstractResponse<String, MathangDTO> {

    private int amountOfConsumption;
}
