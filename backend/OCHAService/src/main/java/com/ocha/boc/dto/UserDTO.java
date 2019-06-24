package com.ocha.boc.dto;

import com.ocha.boc.entity.User;
import com.ocha.boc.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UserDTO {

    private String userId;

    private String phone;
    @Email
    private String email;

    private String photo;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private UserType role;

    public UserDTO(User user) {
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.phone = user.getPhoto();
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.isActive = user.isActive();
        this.role = user.getRole();
        this.userId = user.getId();
    }

}
