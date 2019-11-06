package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class OTPRequest implements Serializable {

    @NotNull
    private String phoneNumber;

    @NotNull
    private String otpCode;

    @NotNull
    private String countryCode;
}
