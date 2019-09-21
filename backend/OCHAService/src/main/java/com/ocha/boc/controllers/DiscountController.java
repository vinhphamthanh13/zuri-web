package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.DiscountRequest;
import com.ocha.boc.request.DiscountUpdateRequest;
import com.ocha.boc.response.DiscountResponse;
import com.ocha.boc.services.impl.DiscountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Slf4j
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    /**
     * Create new Discount
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create New Discount", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/discounts")
    public ResponseEntity<DiscountResponse> createNewDiscount(@RequestBody @Valid DiscountRequest request) {
        log.info("START: create new Discount");
        DiscountResponse response = discountService.createNewDiscount(request);
        log.info("END: create new Discount");
        return ResponseEntity.ok(response);
    }

    /**
     * delete Giam Gia By Id
     *
     * @param giamGiaId
     * @return
     */
    @ApiOperation(value = "Delete Discount By DiscountId", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/discounts/{discountsId}")
    public ResponseEntity<AbstractResponse> deleteDiscountByDiscountId(@PathVariable(value = "giamGiaId") String giamGiaId) {
        log.info("START: delete Discount by id: " + giamGiaId);
        AbstractResponse response = discountService.deleteDiscountByDiscountId(giamGiaId);
        log.info("END: delete Discount by id");
        return ResponseEntity.ok(response);
    }

    /**
     * Update Giam Gia Information
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Update Discount", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/giam-gia")
    public ResponseEntity<DiscountResponse> updateDiscountByDiscountId(@RequestBody DiscountUpdateRequest request) {
        log.info("START: update Discount");
        DiscountResponse response = discountService.updateDiscount(request);
        log.info("END: update Discount");
        return ResponseEntity.ok(response);
    }

}
