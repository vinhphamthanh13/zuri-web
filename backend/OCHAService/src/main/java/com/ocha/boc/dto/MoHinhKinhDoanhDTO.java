package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.MoHinhKinhDoanh;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MoHinhKinhDoanhDTO extends AbstractEntity {

    private String name;

    public MoHinhKinhDoanhDTO(MoHinhKinhDoanh moHinhKinhDoanh) {
        this.name = moHinhKinhDoanh.getName();
    }
}
