package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.NguyenLieu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NguyenLieuDTO extends AbstractEntity {

    private String abbreviations;

    private String name;

    public NguyenLieuDTO(NguyenLieu nguyenLieu) {
        this.id = nguyenLieu.getId();
        this.abbreviations = nguyenLieu.getAbbreviations();
        this.name = nguyenLieu.getName();
    }
}
