package com.ocha.boc.request;

import com.ocha.boc.enums.DanhMucMatHangType;
import com.ocha.boc.enums.MoHinhKinhDoanhType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class CuaHangUpdateRequest implements Serializable {

    @NotNull
    private String cuaHangId;

    @NotNull
    private String phone;

    private String  moHinhKinhDoanhType;

    private String  danhMucMatHangType;

    private String address;

    private String managerEmail;

    private String managerPhone;
}
