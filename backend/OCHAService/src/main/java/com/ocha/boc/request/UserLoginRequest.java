package com.ocha.boc.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserLoginRequest implements Serializable {

    @ApiModelProperty(required=true)
    private String phone;

//    @ApiModelProperty(required=true)
//    private String verificationCode;
//
//    @ApiModelProperty(required=true)
//    private String countryCode;
}
