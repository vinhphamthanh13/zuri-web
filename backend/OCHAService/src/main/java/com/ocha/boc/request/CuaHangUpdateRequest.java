package com.ocha.boc.request;

import com.ocha.boc.enums.DanhMucMatHangType;
import com.ocha.boc.enums.MoHinhKinhDoanhType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CuaHangUpdateRequest implements Serializable {

    private String id;

    private MoHinhKinhDoanhType moHinhKinhDoanhType;

    private DanhMucMatHangType danhMucMatHangType;

    private String address;

    private String managerEmail;
}
