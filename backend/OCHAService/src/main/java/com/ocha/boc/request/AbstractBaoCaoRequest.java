package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AbstractBaoCaoRequest {

    @NotNull
    private String cuaHangId;

    private String fromDate;

    private String toDate;
}
