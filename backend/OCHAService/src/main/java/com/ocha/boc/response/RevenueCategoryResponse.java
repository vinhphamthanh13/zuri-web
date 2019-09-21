package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.entity.HotDealsCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RevenueCategoryResponse extends AbstractResponse {

    List<HotDealsCategory> listHotDealsCategory;

    private String cuaHangId;
}
