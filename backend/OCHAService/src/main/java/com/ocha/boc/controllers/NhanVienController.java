package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.NhanVienRequest;
import com.ocha.boc.response.NhanVienResponse;
import com.ocha.boc.services.impl.NhanVienService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @ApiOperation(value = "Create new Nhan Vien", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/nhan-vien")
    public ResponseEntity<NhanVienResponse> createNewNhanVien(@RequestBody NhanVienRequest request){
        log.info("START: create new nhan vien");
        NhanVienResponse response = nhanVienService.createNewNhanVien(request);
        log.info("END: create new nhan vien");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get List Nhan Vien By Cua Hang Id", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/nhan-vien/{cuaHangId}")
    public ResponseEntity<NhanVienResponse> getListNhanVienByCuaHangId(@PathVariable(value = "cuaHangId") String cuaHangId){
        log.info("START: get list nhan vien by cuaHangId: " + cuaHangId);
        NhanVienResponse response = nhanVienService.getListNhanVienByCuaHangId(cuaHangId);
        log.info("END: get list nhan vien by cuaHangId");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Nhan Vien", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/nhan-vien/{nhanVienId}")
    public ResponseEntity<AbstractResponse> deleteNhanVienByNhanVienId(@PathVariable(value = "nhanVienId") String nhanVienId){
        log.info("START: Delete Nhan Vien: " + nhanVienId);
        AbstractResponse response = nhanVienService.deleteNhanVienByNhanVienId(nhanVienId);
        log.info("END: Delete Nhan Vien: " + nhanVienId);
        return ResponseEntity.ok(response);
    }
}
