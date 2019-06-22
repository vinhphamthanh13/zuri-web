package com.ocha.boc.model;

import java.io.Serializable;

public class GenericModel<T> implements Serializable {

    public static final String ID_ATTR = "id";

    private T id;

    public String getId() {
        if (id != null)
            return id.toString();
        return "";
    }

    public void setId(T id) {
        this.id = id;
    }

}