package com.ocha.boc.controllers;

import com.ocha.boc.response.MatHangBanChayResponse;
import com.ocha.boc.services.impl.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Get List Mat Hang Ban Chay")
    @GetMapping("/order/mat-hang-ban-chay")
    public ResponseEntity<MatHangBanChayResponse> getListMatHangBanChay() {
        MatHangBanChayResponse response = orderService.getListMatHangBanChay();
        return ResponseEntity.ok(response);
    }
}
