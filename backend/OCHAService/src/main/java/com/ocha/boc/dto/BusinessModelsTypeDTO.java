package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.BusinessModelsType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusinessModelsTypeDTO extends AbstractEntity {

    private String name;

    private String id;

    public BusinessModelsTypeDTO(BusinessModelsType businessModelsType) {
        this.id = businessModelsType.getId();
        this.name = businessModelsType.getName();
    }
}
