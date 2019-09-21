package com.ocha.boc.entity;

import com.ocha.boc.enums.EmployeeRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Document(collection = Employee.COLLECTION_NAME)
public class Employee implements Serializable {

    public static final String COLLECTION_NAME = "employee"; //nhan vien

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String restaurantId;

    private String username;

    private String password;

    private String fullName;

    private EmployeeRole employeeRole;

}
