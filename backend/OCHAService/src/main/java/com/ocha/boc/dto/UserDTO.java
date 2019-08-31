package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.CuaHang;
import com.ocha.boc.entity.User;
import com.ocha.boc.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
public class UserDTO extends AbstractEntity {

    private String phone;
    @Email
    private String email;

    private String photo;

    private String name;

    private boolean isActive;

    private UserType role;

    private List<CuaHang> listCuaHang;

    private String lastLoginTime;

    public UserDTO(User user) {
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.name = user.getName();
        this.isActive = user.isActive();
        this.role = user.getRole();
        this.id = user.getId();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.listCuaHang = user.getListCuaHang();
        this.lastLoginTime = user.getLastLoginTime();
    }

}
