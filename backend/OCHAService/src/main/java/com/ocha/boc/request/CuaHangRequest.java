package com.ocha.boc.request;

import com.ocha.boc.enums.DanhMucMatHangType;
import com.ocha.boc.enums.MoHinhKinhDoanhType;

import lombok.*;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CuaHangRequest implements Serializable {

    @NonNull
    private String  moHinhKinhDoanhType;

    @NonNull
    private String  danhMucMatHangType;

    @NonNull
    private String cuaHangName;

    @NonNull
    private String phone;

    private String address;

    @NonNull
    private String managerName;

    @NonNull
    private String managerPhone;

    private String managerEmail;
}
