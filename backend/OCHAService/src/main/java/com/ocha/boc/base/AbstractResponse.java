package com.ocha.boc.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(value = Include.NON_EMPTY)
public class AbstractResponse<ID, T> {
    Boolean success = Boolean.FALSE;
    ID objectId;
    T object;
    List<T> objects;
    Long totalResultCount;
    String message;
}
