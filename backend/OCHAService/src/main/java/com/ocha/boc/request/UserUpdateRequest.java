package com.ocha.boc.request;

import com.ocha.boc.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {

    @NonNull
    private String userId;

    @Email
    @NonNull
    private String email;

    private String photo;

    private String name;

    private UserType role;

    @NonNull
    private String verificationCode;

    @NonNull
    private String countryCode;

    @NonNull
    private String phoneNumber;

}
