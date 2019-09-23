package com.ocha.boc.entity;

import com.ocha.boc.base.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@ToString
@Document(collection = SystemConfig.COLLECTION_NAME)
public class SystemConfig extends AbstractEntity {

    public static final String COLLECTION_NAME = "systemconfig";



}
