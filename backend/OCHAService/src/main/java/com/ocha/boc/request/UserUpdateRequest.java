package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {

    private String userId;

    private String email;

    private String photo;

    private String firstName;

    private String lastName;


}
