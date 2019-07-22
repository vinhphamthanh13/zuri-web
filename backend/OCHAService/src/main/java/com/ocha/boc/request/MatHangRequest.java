package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;

@Getter
@Setter
public class MatHangRequest implements Serializable {

    private String name;

    private String bangGiaId;

    private String danhMucId;

    private String khuyenMaiId;

    private String cuaHangId;
}
