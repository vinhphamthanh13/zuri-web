package com.ocha.boc.controllers;

import com.ocha.boc.request.OrderRequest;
import com.ocha.boc.response.OrderResponse;
import com.ocha.boc.services.impl.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("Initial Order")
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> initialOrder(@RequestBody OrderRequest request){
        log.info("START: initial orders");
        OrderResponse response = orderService.initialOrder(request);
        log.info("END: initial orders");
        return ResponseEntity.ok(response);
    }
}
