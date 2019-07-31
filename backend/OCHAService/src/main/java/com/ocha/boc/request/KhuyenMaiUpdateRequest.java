package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class KhuyenMaiUpdateRequest implements Serializable {

    private String khuyenMaiId;

    private double rate;

    private String fromDate;

    private String toDate;

    @NonNull
    private String cuaHangId;
}
