package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.EmployeeRequest;
import com.ocha.boc.response.EmployeeResponse;
import com.ocha.boc.services.impl.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Create New Employee
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create new Employee", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponse> createNewEmployee(@RequestBody EmployeeRequest request) {
        log.info("START: create new Employee");
        EmployeeResponse response = employeeService.createNewEmployee(request);
        log.info("END: create new Employee");
        return ResponseEntity.ok(response);
    }

    /**
     * Get List Employee By Restaurant Id
     *
     * @param restaurantId
     * @return
     */
    @ApiOperation(value = "Get List Employee By Restaurant Id", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/employees/{restaurantId}")
    public ResponseEntity<EmployeeResponse> getListEmployeeByRestaurantId(@PathVariable(value = "cuaHangId") String restaurantId) {
        log.info("START: get list employees by restaurantId: " + restaurantId);
        EmployeeResponse response = employeeService.getListEmployeeByRestaurantId(restaurantId);
        log.info("END: get list employees by restaurantId");
        return ResponseEntity.ok(response);
    }

    /**
     * Delete Employee By Employee Id
     *
     * @param employeeId
     * @return
     */
    @ApiOperation(value = "Delete Employee", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/Employees/{employeeId}")
    public ResponseEntity<AbstractResponse> deleteEmployeeByEmployeeId(@PathVariable(value = "employeeId") String employeeId) {
        log.info("START: Delete Employee: " + employeeId);
        AbstractResponse response = employeeService.deleteEmployeeByEmployeeId(employeeId);
        log.info("END: Delete Employee: " + employeeId);
        return ResponseEntity.ok(response);
    }
}
