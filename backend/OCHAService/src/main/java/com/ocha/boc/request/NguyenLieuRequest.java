package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class NguyenLieuRequest implements Serializable {

    private String abbreviations;

    private String name;

}
