package com.ocha.boc.dto;

import com.ocha.boc.entity.Employee;
import com.ocha.boc.enums.EmployeeRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeDTO {

    private String restaurantId;

    private String username;

    private String password;

    private String fullName;

    private EmployeeRole employeeRole;

    public EmployeeDTO(Employee employee) {
        this.restaurantId = employee.getRestaurantId();
        this.username = employee.getUsername();
        this.password = employee.getPassword();
        this.fullName = employee.getFullName();
        this.employeeRole = employee.getEmployeeRole();
    }
}
