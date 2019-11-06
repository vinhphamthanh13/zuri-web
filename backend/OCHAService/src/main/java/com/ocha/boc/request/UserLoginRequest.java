package com.ocha.boc.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserLoginRequest implements Serializable {

    @NotNull
    private String phone;
}
