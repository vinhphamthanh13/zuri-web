package com.ocha.boc.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ocha.boc.base.AbstractResponseBean;
import com.ocha.boc.model.SampleUserData;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(value = Include.NON_NULL)
@Getter
@Setter
public class SampleResponse extends AbstractResponseBean<String, SampleUserData> {

}
