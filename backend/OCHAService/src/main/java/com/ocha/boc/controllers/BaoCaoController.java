package com.ocha.boc.controllers;

import com.ocha.boc.response.DoanhThuTongQuanResponse;
import com.ocha.boc.services.impl.BaoCaoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BaoCaoController {

    @Autowired
    private BaoCaoService baoCaoService;

    @ApiOperation("Doanh Thu Tổng Quan")
    @GetMapping("/doanh-thu-tong-quan/{cuaHangId}")
    public ResponseEntity<DoanhThuTongQuanResponse> getDoanhThuTongQuan(@PathVariable(value = "cuaHangId") String cuaHangId){
        log.info("START: get current doanh thu tong quan");
        DoanhThuTongQuanResponse response = baoCaoService.getDoanhThuTongQuan(cuaHangId);
        log.info("END: get current doanh thu tong quan");
        return ResponseEntity.ok(response);
    }

//    @ApiOperation("Get Doanh Thu Tong Quan in range")
//    @GetMapping("/doanh-thu-tong-quan")
//    public ResponseEntity<DoanhThuTongQuanResponse> getDoanhThuTongQuanInRangeDate(){
//
//    }

}
