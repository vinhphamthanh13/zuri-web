package com.ocha.boc.request;

import com.ocha.boc.enums.DanhMucMatHangType;
import com.ocha.boc.enums.MoHinhKinhDoanhType;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CuaHangRequest implements Serializable {

    @ApiModelProperty(required=true)
    private MoHinhKinhDoanhType moHinhKinhDoanhType;

    @ApiModelProperty(required = true)
    private DanhMucMatHangType danhMucMatHangType;

    @ApiModelProperty(required=true)
    private String cuaHangName;

    @ApiModelProperty(required=true)
    private String phone;

    private String address;

    @ApiModelProperty(required=true)
    private String managerName;

    @ApiModelProperty(required=true)
    private String managerPhone;

    private String managerEmail;
}
