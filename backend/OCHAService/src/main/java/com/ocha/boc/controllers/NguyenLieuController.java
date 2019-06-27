package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.NguyenLieuRequest;
import com.ocha.boc.response.NguyenLieuResponse;
import com.ocha.boc.services.impl.NguyenLieuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NguyenLieuController {

    @Autowired
    private NguyenLieuService nguyenLieuService;

    @ApiOperation(value = "Create new Nguyen Lieu")
    @PostMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> newNguyenLieu(@RequestBody NguyenLieuRequest request) {
        NguyenLieuResponse response = nguyenLieuService.newNguyenLieu(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "update NguyenLieu information")
    @PutMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> updateNguyenLieuInformation(@RequestBody NguyenLieuRequest request) {
        NguyenLieuResponse response = nguyenLieuService.updateNguyenLieuInformation(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find NguyenLieu by Id")
    @GetMapping("/nguyen-lieu/{id}")
    public ResponseEntity<NguyenLieuResponse> findNguyenLieuById(@PathVariable String  id){
        NguyenLieuResponse response = nguyenLieuService.findNguyenLieuById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all Nguyen Lieu")
    @GetMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> getAllNguyenLieu(){
        NguyenLieuResponse response = nguyenLieuService.getAllNguyenLieu();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Nguyen Lieu By Id")
    @DeleteMapping("/nguyen-lieu/{id}")
    public ResponseEntity<AbstractResponse> deleteNguyenLieuById(@PathVariable String id){
        AbstractResponse response = nguyenLieuService.deleteNguyenLieuById(id);
        return ResponseEntity.ok(response);
    }

}
