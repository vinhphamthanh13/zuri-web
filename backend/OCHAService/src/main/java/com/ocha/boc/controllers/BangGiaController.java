package com.ocha.boc.controllers;

import com.ocha.boc.request.BangGiaRequest;
import com.ocha.boc.request.BangGiaUpdateRequest;
import com.ocha.boc.response.BangGiaResponse;
import com.ocha.boc.services.impl.BangGiaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BangGiaController {

    @Autowired
    private BangGiaService bangGiaService;

    @ApiOperation(value = "Create new Bang Gia")
    @PostMapping("/bang-gia")
    public ResponseEntity<BangGiaResponse> createNewBangGia(@RequestBody BangGiaRequest request) {
        BangGiaResponse response = bangGiaService.createNewBangGia(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update Bang Gia Information")
    @PutMapping("/bang-gia")
    public ResponseEntity<BangGiaResponse> updateBangGiaInformation(@RequestBody BangGiaUpdateRequest request) {
        BangGiaResponse response = bangGiaService.updateBangGiaInformation(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find Bang Gia by Id")
    @GetMapping("/bang-gia/{id}")
    public ResponseEntity<BangGiaResponse> findBangGiaById(@PathVariable String id) {
        BangGiaResponse response = bangGiaService.findBangGiaById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all Bang Gia")
    @GetMapping("/bang-gia")
    public ResponseEntity<BangGiaResponse> getAllBangGia() {
        BangGiaResponse response = bangGiaService.getAllBangGia();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Bang Gia By Id")
    @DeleteMapping("/bang-gia/{id}")
    public ResponseEntity<BangGiaResponse> deleteBangGiaById(@PathVariable String id) {
        BangGiaResponse response = bangGiaService.deleteBangGiaById(id);
        return ResponseEntity.ok(response);
    }
}
