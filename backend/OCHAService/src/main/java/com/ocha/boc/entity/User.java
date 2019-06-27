package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

/**
 * Entity Object presents for User
 */
@Getter
@Setter
@Document(collection = User.COLLECTION_NAME)
public class User extends AbstractEntity {

    public static final String COLLECTION_NAME = "user";

    private String phone;

    @Email
    private String email;

    private String photo;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private UserType role;

    public User() {

    }

    public User(UserDTO userDTO) {
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.phone = userDTO.getPhoto();
        this.lastName = userDTO.getLastName();
        this.firstName = userDTO.getFirstName();
        this.isActive = userDTO.isActive();
        this.role = userDTO.getRole();
        this.id = userDTO.getId();
    }
}
