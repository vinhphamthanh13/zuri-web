package com.ocha.boc.controllers;

import com.ocha.boc.response.MatHangBanChayResponse;
import com.ocha.boc.response.OrderResponse;
import com.ocha.boc.services.impl.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "Get List Mat Hang Ban Chay")
    @GetMapping("/order/mat-hang-ban-chay")
    public ResponseEntity<MatHangBanChayResponse> getListMatHangBanChay() {
        log.info("START: getListMatHangBanChay");
        MatHangBanChayResponse response = orderService.getListMatHangBanChay();
        log.info("END: getListMatHangBanChay");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get List Mat Hang Ban Chay By Date")
    @GetMapping("/order/mat-hang-ban-chay/{date}")
    public ResponseEntity<MatHangBanChayResponse> getListMatHangBanChayByDate(@PathVariable String date) {
        log.info("START: getListMatHangBanChayByDate " + date);
        MatHangBanChayResponse response = orderService.getListMatHangBanChayByDate(date);
        log.info("END: getListMatHangBanChayByDate");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get List Order By Date")
    @GetMapping("/order/{date}")
    public ResponseEntity<OrderResponse> findListOrderByDate(@PathVariable String date){
        log.info("START: findListOrderByDate " + date);
        OrderResponse response = orderService.findListOrderByDate(date);
        log.info("END: findListOrderByDate");
        return ResponseEntity.ok(response);
    }
}
