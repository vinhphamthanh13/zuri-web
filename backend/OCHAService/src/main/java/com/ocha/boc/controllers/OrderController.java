package com.ocha.boc.controllers;

import com.ocha.boc.request.*;
import com.ocha.boc.response.OrderResponse;
import com.ocha.boc.services.impl.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("Initial Order")
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> initialOrder(@RequestBody OrderRequest request) {
        log.info("START: initial orders");
        OrderResponse response = orderService.initialOrder(request);
        log.info("END: initial orders");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update Order Information")
    @PutMapping("/orders")
    public ResponseEntity<OrderResponse> updateOrderInformation(@RequestBody OrderUpdateRequest request) {
        log.info("START: update order: " + request.getOrderId());
        OrderResponse response = orderService.updateOrderInformation(request);
        log.info("END: update order");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Cancel Order")
    @PostMapping("/orders/reject")
    public ResponseEntity<OrderResponse> rejectOrder(@RequestBody OrderRejectObjectRequest request) {
        log.info("START: reject order: " + request.getOrderId());
        OrderResponse response = orderService.rejectOrder(request);
        log.info("END: reject order");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Checkout Order")
    @PostMapping("/orders/checkout")
    public ResponseEntity<OrderResponse> checkoutOrder(@RequestBody OrderCheckoutObjectRequest request) {
        log.info("START: checkout order: " + request.getOrderId());
        OrderResponse response = orderService.checkoutOrder(request);
        log.info("END: checkout order");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get Order By CuaHangId")
    @PostMapping("/orders/{cuaHangId}")
    public ResponseEntity<OrderResponse> getOrdersByCuaHangId(@RequestBody PageRequest pageRequest, @PathVariable(value = "cuaHangId") String cuaHangId){
        log.info("START: get orders ");
        OrderResponse response = orderService.getOrdersByCuaHangId(pageRequest, cuaHangId);
        log.info("END: get orders by cuaHangId");
        return ResponseEntity.ok(response);
    }
}
