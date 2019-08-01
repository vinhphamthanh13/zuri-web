package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.DanhMucRequest;
import com.ocha.boc.request.DanhMucUpdateRequest;
import com.ocha.boc.response.DanhMucResponse;
import com.ocha.boc.services.impl.DanhMucService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DanhMucController {

    @Autowired
    private DanhMucService danhMucService;

    @ApiOperation(value = "Create new Danh Muc")
    @PostMapping("/danh-muc")
    public ResponseEntity<DanhMucResponse> createNewDanhMuc(@RequestBody DanhMucRequest request) {
        DanhMucResponse response = danhMucService.createNewDanhMuc(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update Danh Muc")
    @PutMapping("/danh-muc")
    public ResponseEntity<DanhMucResponse> updateDanhMuc(@RequestBody DanhMucUpdateRequest request) {
        DanhMucResponse response = danhMucService.updateDanhMuc(request);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Find Danh muc By DanhMucId")
    @GetMapping("/danh-muc/{cuaHangId}/{id}")
    public ResponseEntity<DanhMucResponse> findDanhMucById( @PathVariable("cuaHangId") String cuaHangId,@PathVariable("id") String id) {
        DanhMucResponse response = danhMucService.findDanhMucByDanhMucId(id, cuaHangId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all Danh Muc")
    @GetMapping("/danh-muc")
    public ResponseEntity<DanhMucResponse> getAllDanhMuc(@RequestParam String cuaHangId) {
        DanhMucResponse response = danhMucService.getAllDanhMuc(cuaHangId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Danh Muc By DanhMucId")
    @DeleteMapping("/danh-muc/{cuaHangId}/{id}")
    public ResponseEntity<AbstractResponse> deleteDanhMucById(@PathVariable("cuaHangId") String cuaHangId,@PathVariable("id") String id) {
        AbstractResponse response = danhMucService.deleteDanhMucByDanhMucId(id, cuaHangId);
        return ResponseEntity.ok(response);
    }

}
