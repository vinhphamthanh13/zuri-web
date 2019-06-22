package com.ocha.boc.entity;

import com.ocha.boc.model.GenericModel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity Object presents for User
 */
@Getter
@Setter
@Document(collection = User.COLLECTION_NAME)
public class User extends GenericModel<ObjectId> {

    public static final String COLLECTION_NAME = "user";

    private String phone;

    private String email;

    private String photo;

    private String firstName;

    private String lastName;

    private long openDate;

    private boolean isActive;

}
