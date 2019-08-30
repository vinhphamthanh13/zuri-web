package com.ocha.boc.controllers;

import com.ocha.boc.request.CuaHangRequest;
import com.ocha.boc.request.CuaHangUpdateRequest;
import com.ocha.boc.response.CuaHangResponse;
import com.ocha.boc.services.impl.CuaHangService;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class CuaHangController {

    @Autowired
    private CuaHangService cuaHangService;

    @ApiOperation(value = "Create new Cua Hang", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/cua-hang")
    public ResponseEntity<CuaHangResponse> createCuaHang(@RequestBody CuaHangRequest request) {
        log.info("START: create new cua hang");
        CuaHangResponse response = cuaHangService.createCuaHang(request);
        log.info("END: create new cua hang");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update Cua Hang Information", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/cua-hang")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CuaHangResponse> updateEmailCuaHang(@RequestBody CuaHangUpdateRequest request) {
        log.info("START: update cua hang information ");
        CuaHangResponse response = cuaHangService.updateEmailCuaHang(request);
        log.info("END: update cua hang information");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Find Cua Hang Information by CuaHangId", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/cua-hang/{cuaHangId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CuaHangResponse> findCuaHangByCuaHangId(@PathVariable(value = "cuaHangId") String cuaHangId) {
        log.info("START: find cua hang information by cuaHangId: " + cuaHangId);
        CuaHangResponse response = cuaHangService.findCuaHangByCuaHangId(cuaHangId);
        log.info("END: find cua hang information by cuaHangId");
        return ResponseEntity.ok(response);
    }

}
