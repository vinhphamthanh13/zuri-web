package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.NguyenLieuRequest;
import com.ocha.boc.response.NguyenLieuResponse;
import com.ocha.boc.services.impl.NguyenLieuService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NguyenLieuController {

    @Autowired
    private NguyenLieuService nguyenLieuService;

    /**
     * Create New Nguyen Lieu
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create new Nguyen Lieu", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> newNguyenLieu(@RequestBody NguyenLieuRequest request) {
        NguyenLieuResponse response = nguyenLieuService.newNguyenLieu(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Update Nguyen Lieu Information
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "update NguyenLieu information", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> updateNguyenLieuInformation(@RequestBody NguyenLieuRequest request) {
        NguyenLieuResponse response = nguyenLieuService.updateNguyenLieuInformation(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Find Nguyen Lieu By Id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Find NguyenLieu by Id", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/nguyen-lieu/{id}")
    public ResponseEntity<NguyenLieuResponse> findNguyenLieuById(@PathVariable String id) {
        NguyenLieuResponse response = nguyenLieuService.findNguyenLieuById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get All Nguyen Lieu
     *
     * @return
     */
    @ApiOperation(value = "Get all Nguyen Lieu", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> getAllNguyenLieu() {
        NguyenLieuResponse response = nguyenLieuService.getAllNguyenLieu();
        return ResponseEntity.ok(response);
    }

    /**
     * Delete Nguyen Lieu By Id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Delete Nguyen Lieu By Id", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/nguyen-lieu/{id}")
    public ResponseEntity<AbstractResponse> deleteNguyenLieuById(@PathVariable String id) {
        AbstractResponse response = nguyenLieuService.deleteNguyenLieuById(id);
        return ResponseEntity.ok(response);
    }

}
