package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.dto.UserDTO;
import com.ocha.boc.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

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

    private String name;

    private boolean isActive;

    private UserType role;

    private List<CuaHang> listCuaHang = new ArrayList<>();

    public User() {

    }

    public User(UserDTO userDTO) {
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.phone = userDTO.getPhoto();
        this.name = userDTO.getName();
        this.isActive = userDTO.isActive();
        this.role = userDTO.getRole();
        this.id = userDTO.getId();
        this.listCuaHang = userDTO.getListCuaHang();
    }
}
