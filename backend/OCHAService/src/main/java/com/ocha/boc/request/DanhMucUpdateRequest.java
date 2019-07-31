package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class DanhMucUpdateRequest implements Serializable {

    private String abbreviations;

    private String name;

    private String danhMucId;

    @NonNull
    private String cuaHangId;
}
