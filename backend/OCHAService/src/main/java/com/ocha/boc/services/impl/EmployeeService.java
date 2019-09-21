package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.EmployeeDTO;
import com.ocha.boc.entity.Employee;
import com.ocha.boc.repository.EmployeeRepository;
import com.ocha.boc.request.EmployeeRequest;
import com.ocha.boc.response.EmployeeResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponse createNewEmployee(EmployeeRequest request) {
        EmployeeResponse response = new EmployeeResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_EMPLOYEE_FAIL);
        try {
            if (employeeRepository.existsByUsername(request.getUsername())) {
                response.setMessage(CommonConstants.USERNAME_EXISTED);
            } else {
                Employee employee = new Employee();
                employee.setRestaurantId(request.getRestaurantId());
                employee.setFullName(request.getFullName());
                employee.setEmployeeRole(request.getEmployeeRole());
                employee.setPassword(request.getPassword());
                employee.setUsername(request.getUsername());
                employee.setCreatedDate(DateUtils.getCurrentDateAndTime());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new EmployeeDTO(employee));
                employeeRepository.save(employee);
            }
        } catch (Exception e) {
            log.error("Error when create new employee: {}", e);
        }
        return response;
    }

    public EmployeeResponse getListEmployeeByRestaurantId(String restaurantId) {
        EmployeeResponse response = new EmployeeResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_LIST_EMPLOYEE_BY_RESTAURANT_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(restaurantId)) {
                List<Employee> temp = employeeRepository.findAllByRestaurantId(restaurantId);
                if (CollectionUtils.isNotEmpty(temp)) {
                    List<EmployeeDTO> result = new ArrayList<EmployeeDTO>();
                    for (Employee employee : temp) {
                        result.add(new EmployeeDTO(employee));
                    }
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObjects(result);
                    response.setTotalResultCount((long) result.size());
                } else {
                    response.setMessage(CommonConstants.NOT_EXISTED_ANY_EMPLOYEE);
                }

            }
        } catch (Exception e) {
            log.error("Error when get list employees: {}", e);
        }
        return response;
    }

    public AbstractResponse deleteEmployeeByEmployeeId(String employeeId) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_EMPLOYEE_BY_EMPLOYEE_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(employeeId)) {
                if (employeeRepository.existsById(employeeId)) {
                    Optional<Employee> optEmployee = employeeRepository.findEmployeeById(employeeId);
                    employeeRepository.delete(optEmployee.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                } else {
                    response.setMessage(CommonConstants.EMPLOYEE_IS_NOT_EXISTED);
                }
            }
        } catch (Exception e) {
            log.error("Error when delete employee: ", e);
        }
        return response;
    }
}
