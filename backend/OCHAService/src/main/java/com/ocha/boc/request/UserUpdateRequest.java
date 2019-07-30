package com.ocha.boc.request;

import com.ocha.boc.enums.UserType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {

    @NonNull
    private String userId;
    @Email
    private String email;

    private String photo;

    private String firstName;

    private String lastName;

    private UserType role;

    private String verificationCode;

    private String countryCode;

    private String phoneNumber;

}
