package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.MatHangRequest;
import com.ocha.boc.request.MatHangUpdateRequest;
import com.ocha.boc.response.MatHangResponse;
import com.ocha.boc.services.impl.MatHangService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MatHangController {

    @Autowired
    private MatHangService matHangService;

    @ApiOperation(value = "Create new Mat Hang")
    @PostMapping("/mat-hang")
    public ResponseEntity<MatHangResponse> createNewMatHang(@RequestBody MatHangRequest request) {
        MatHangResponse response = matHangService.createNewMatHang(request);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Update Mat Hang")
    @PutMapping("/mat-hang/{id}")
    public ResponseEntity<MatHangResponse> updateMatHangInfor(@RequestBody MatHangUpdateRequest request) {
        MatHangResponse response = matHangService.updateMatHangInfor(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find Mat Hang By Id")
    @GetMapping("/mat-hang/{id}")
    public ResponseEntity<MatHangResponse> findMatHangById(@PathVariable String id) {
        MatHangResponse response = matHangService.findMatHangById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get All Mat Hang")
    @GetMapping("/mat-hang")
    public ResponseEntity<MatHangResponse> getAllMatHang() {
        MatHangResponse response = matHangService.getAllMatHang();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Mat Hang By Id")
    @DeleteMapping("/mat-hang/{id}")
    public ResponseEntity<AbstractResponse> deleteMatHangById(@PathVariable String id) {
        MatHangResponse response = matHangService.deleteMatHangById(id);
        return ResponseEntity.ok(response);
    }
}
