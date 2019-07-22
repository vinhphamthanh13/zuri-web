package com.ocha.boc.controllers;

import com.ocha.boc.request.CuaHangRequest;
import com.ocha.boc.request.CuaHangUpdateRequest;
import com.ocha.boc.response.CuaHangResponse;
import com.ocha.boc.services.impl.CuaHangService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class CuaHangController {

    @Autowired
    private CuaHangService cuaHangService;

    @ApiOperation("Create new Cua Hang")
    @PostMapping("/cua-hang")
    public ResponseEntity<CuaHangResponse> createCuaHang(@RequestBody CuaHangRequest request){
        log.info("START: create new cua hang");
        CuaHangResponse response = cuaHangService.createCuaHang(request);
        log.info("END: create new cua hang");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update Cua Hang Information")
    @PutMapping("/cua-hang/email")
    public ResponseEntity<CuaHangResponse> updateEmailCuaHang(@RequestBody CuaHangUpdateRequest request){
        log.info("START: update email cua hang");
        CuaHangResponse response = cuaHangService.updateEmailCuaHang(request);
        log.info("END: update email cua hang");
        return ResponseEntity.ok(response);
    }

}
