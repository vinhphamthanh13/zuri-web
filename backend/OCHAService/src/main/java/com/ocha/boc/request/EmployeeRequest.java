package com.ocha.boc.request;

import com.ocha.boc.enums.EmployeeRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class EmployeeRequest implements Serializable {

    @NotNull
    private String restaurantId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String fullName;

    @NotNull
    private EmployeeRole employeeRole;
}
