package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatHangListRequest extends PageRequest {

    private String cuaHangId;

    private String name;
}
