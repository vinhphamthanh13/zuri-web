package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageRequest {
    private Integer page;
    private Integer size;
    private String sort;
}
