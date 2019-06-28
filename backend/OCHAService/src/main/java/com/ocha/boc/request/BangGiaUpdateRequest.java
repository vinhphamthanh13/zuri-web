package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BangGiaUpdateRequest implements Serializable {

    private String id;

    private int numberOfPrice;
}
