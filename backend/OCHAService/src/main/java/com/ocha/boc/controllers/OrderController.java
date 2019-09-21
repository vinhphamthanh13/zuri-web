package com.ocha.boc.controllers;

import com.ocha.boc.request.*;
import com.ocha.boc.response.OrderResponse;
import com.ocha.boc.services.impl.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Initial Order
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Initial Order", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> initialOrder(@RequestBody OrderRequest request) {
        log.info("START: initial orders");
        OrderResponse response = orderService.initialOrder(request);
        log.info("END: initial orders");
        return ResponseEntity.ok(response);
    }

    /**
     * Update Order Information
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Update Order Information", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/orders")
    public ResponseEntity<OrderResponse> updateOrderInformation(@RequestBody OrderUpdateRequest request) {
        log.info("START: update order: " + request.getOrderId());
        OrderResponse response = orderService.updateOrderInformation(request);
        log.info("END: update order");
        return ResponseEntity.ok(response);
    }

    /**
     * Reject Order
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Cancel Order", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/orders/reject")
    public ResponseEntity<OrderResponse> rejectOrder(@RequestBody OrderRejectObjectRequest request) {
        log.info("START: reject order: " + request.getOrderId());
        OrderResponse response = orderService.rejectOrder(request);
        log.info("END: reject order");
        return ResponseEntity.ok(response);
    }

    /**
     * Checkout Order
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Checkout Order", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/orders/checkout")
    public ResponseEntity<OrderResponse> checkoutOrder(@RequestBody OrderCheckoutObjectRequest request) {
        log.info("START: checkout order: " + request.getOrderId());
        OrderResponse response = orderService.checkoutOrder(request);
        log.info("END: checkout order");
        return ResponseEntity.ok(response);
    }

    /**
     * Get Orders By RestaurantId
     *
     * @param pageRequest
     * @param restaurantId
     * @return
     */
    @ApiOperation(value = "Get Order By RestaurantId", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/orders/{restaurantId}")
    public ResponseEntity<OrderResponse> getOrdersByRestaurantId(@RequestBody PageRequest pageRequest, @PathVariable(value = "restaurantId") String restaurantId) {
        log.info("START: get orders ");
        OrderResponse response = orderService.getOrdersByRestaurantId(pageRequest, restaurantId);
        log.info("END: get orders by restaurantId");
        return ResponseEntity.ok(response);
    }
}
