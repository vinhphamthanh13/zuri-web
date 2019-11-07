package com.ocha.boc.request;

import com.ocha.boc.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {

    @NotNull
    private String userId;

    @Email
    @NotNull
    private String email;

    private String photo;

    private String name;

    private UserType role;

    @NotNull
    private String verificationCode;

    @NotNull
    private String countryCode;

    @NotNull
    private String phoneNumber;

}
