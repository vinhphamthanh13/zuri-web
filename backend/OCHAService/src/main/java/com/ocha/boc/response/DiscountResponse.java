package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class DiscountResponse extends AbstractResponse<String, Object> implements Serializable {
}
