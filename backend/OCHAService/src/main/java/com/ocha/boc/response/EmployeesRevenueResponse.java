package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.entity.EmployeesRevenue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class EmployeesRevenueResponse extends AbstractResponse implements Serializable {

    private String restaurantId;

    private List<EmployeesRevenue> listEmployeeSRevenue;

}
