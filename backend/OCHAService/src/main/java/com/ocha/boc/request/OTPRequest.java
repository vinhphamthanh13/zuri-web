package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class OTPRequest implements Serializable {

    private String phoneNumber;

    private String optCode;

    private String countryCode;
}
