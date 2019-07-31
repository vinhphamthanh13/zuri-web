package com.ocha.boc.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserLoginRequest implements Serializable {

    @NonNull
    private String phone;

    @NonNull
    private String verificationCode;

    @NonNull
    private String countryCode;
}
