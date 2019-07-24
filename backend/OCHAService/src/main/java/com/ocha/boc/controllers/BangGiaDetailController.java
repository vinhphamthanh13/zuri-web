package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.BangGiaDetailRequest;
import com.ocha.boc.request.BangGiaDetailUpdateRequest;
import com.ocha.boc.response.BangGiaDetailResponse;
import com.ocha.boc.services.impl.BangGiaDetailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BangGiaDetailController {

    @Autowired
    private BangGiaDetailService bangGiaDetailService;

    @ApiOperation(value = "Create new Bang Gia Detail")
    @PostMapping("/thong-tin-bang-gia")
    public ResponseEntity<BangGiaDetailResponse> createNewBangGiaDetail(@RequestBody BangGiaDetailRequest request) {
        BangGiaDetailResponse response = bangGiaDetailService.createNewBangGiaDetail(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update Bang Gia Detail")
    @PutMapping("/thong-tin-bang-gia")
    public ResponseEntity<BangGiaDetailResponse> updateBangGiaDetail(@RequestBody BangGiaDetailUpdateRequest request) {
        BangGiaDetailResponse response = bangGiaDetailService.updateBangGiaDetail(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find Bang Gia Detail By Id")
    @GetMapping("/thong-tin-bang-gia/{id}")
    public ResponseEntity<BangGiaDetailResponse> findBangGiaDetailById(@PathVariable("id") String id) {
        BangGiaDetailResponse response = bangGiaDetailService.findBangGiaDetailById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find Bang Gia Detail By BangGiaId")
    @GetMapping("/bang-gia/thong-tin-bang-gia/{id}")
    public ResponseEntity<BangGiaDetailResponse> findBangGiaDetailByBangGiaId(@PathVariable("id") String id) {
        BangGiaDetailResponse response = bangGiaDetailService.findBangGiaDetailByBangGiaId(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all BangGiaDetail")
    @GetMapping("/thong-tin-bang-gia")
    public ResponseEntity<BangGiaDetailResponse> getAllBangGiaDetail(){
        BangGiaDetailResponse response = bangGiaDetailService.getAllBangGiaDetail();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Bang Gia Detail By Id")
    @DeleteMapping("/thong-tin-bang-gia/{id}")
    public ResponseEntity<AbstractResponse> deleteBangGiaDetailById(@PathVariable("id") String id){
        AbstractResponse response = bangGiaDetailService.deleteBangGiaDetailById(id);
        return ResponseEntity.ok(response);
    }

}
