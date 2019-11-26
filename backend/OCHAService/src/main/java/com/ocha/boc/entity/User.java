package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.enums.UserType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Object presents for User
 */
@Data
@Builder
@Document(collection = User.COLLECTION_NAME)
public class User extends AbstractEntity {

    public static final String COLLECTION_NAME = "user";

    private String phone;

    @Email
    private String email;

    private String photo;

    private String name;

    private boolean isActive;

    private UserType role;

    private List<String> listRestaurant = new ArrayList<>();

    private String lastLoginTime;

}
