package com.ocha.boc.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = Include.NON_EMPTY)
public class AbstractResponseBean<ID, T> {
    Boolean success = Boolean.FALSE;
    ID objectId;
    T object;
    List<T> objects;
    Long totalResultCount;
    Long currentPageIndex;
    Long resultPerPage;
    String message;
}
