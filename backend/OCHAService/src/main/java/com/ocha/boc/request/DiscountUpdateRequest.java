package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DiscountUpdateRequest extends DiscountRequest {

    @NotNull
    private String discountId;


}
