package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageRequest {
    private Integer page =0;
    private Integer size = 1;
    private String sort="createdDate,asc";
    private String search;
}
