package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.DanhMucRequest;
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
    public ResponseEntity<DanhMucResponse> updateDanhMuc(@RequestBody DanhMucRequest request) {
        DanhMucResponse response = danhMucService.updateDanhMuc(request);
        return ResponseEntity.ok(response);
    }


    @ApiOperation(value = "Find Danh muc By Id")
    @GetMapping("/danh-muc/{id}")
    public ResponseEntity<DanhMucResponse> findDanhMucById(@PathVariable String id){
        DanhMucResponse response = danhMucService.findDanhMucById(id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all Danh Muc")
    @GetMapping("/danh-muc")
    public ResponseEntity<DanhMucResponse> getAllDanhMuc(){
        DanhMucResponse response = danhMucService.getAllDanhMuc();
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Danh Muc By Id")
    @DeleteMapping("/danh-muc/{id}")
    public ResponseEntity<AbstractResponse> deleteDanhMucById(@PathVariable String  id){
        AbstractResponse response = danhMucService.deleteDanhMucById(id);
        return ResponseEntity.ok(response);
    }
}
