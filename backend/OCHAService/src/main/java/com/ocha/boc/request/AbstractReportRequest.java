package com.ocha.boc.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AbstractReportRequest {

    @NotNull
    private String restaurantId;

    private String fromDate;

    private String toDate;
}
