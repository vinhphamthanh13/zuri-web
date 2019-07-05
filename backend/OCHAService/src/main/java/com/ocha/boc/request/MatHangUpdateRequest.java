package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MatHangUpdateRequest implements Serializable {

    private String id;

    private String name;

    private String bangGiaId;

    private String danhMucId;

    private String khuyenMaiId;
}
