package com.ocha.boc.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class AbstractEntity {
    @Id
    protected String id;
    protected String createdDate;
    protected String lastModifiedDate;
}
