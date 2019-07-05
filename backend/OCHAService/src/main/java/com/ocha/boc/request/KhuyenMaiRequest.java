package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class KhuyenMaiRequest implements Serializable {


    private double rate;

    private String fromDate;

    private String toDate;
}
