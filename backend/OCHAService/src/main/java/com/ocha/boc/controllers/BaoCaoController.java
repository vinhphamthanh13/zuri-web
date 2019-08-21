package com.ocha.boc.controllers;

import com.ocha.boc.request.AbstractBaoCaoRequest;
import com.ocha.boc.response.*;
import com.ocha.boc.services.impl.BaoCaoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class BaoCaoController {

    @Autowired
    private BaoCaoService baoCaoService;

    @ApiOperation("Doanh Thu Tổng Quan")
    @GetMapping("/doanh-thu-tong-quan")
    public ResponseEntity<DoanhThuTongQuanResponse> getDoanhThuTongQuan(@RequestParam(value = "cuaHangId") String cuaHangId){
        log.info("START: get current doanh thu tong quan");
        DoanhThuTongQuanResponse response = baoCaoService.getDoanhThuTongQuan(cuaHangId);
        log.info("END: get current doanh thu tong quan");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Get Doanh Thu Tong Quan In Range Date")
    @PostMapping("/doanh-thu-tong-quan")
    public ResponseEntity<DoanhThuTongQuanResponse> getDoanhThuTongQuanInRangeDate(@RequestBody AbstractBaoCaoRequest request){
        log.info("START: get doanh thu tong quan from: " + request.getFromDate() + " to: " + request.getToDate());
        DoanhThuTongQuanResponse response = baoCaoService.getDoanhThuTongQuanInRangeDate(request);
        log.info("END: get doanh thu tong quan in range date");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Doanh Thu Theo Danh Mục")
    @GetMapping("/doanh-thu-theo-danh-muc")
    public ResponseEntity<DoanhThuTheoDanhMucResponse> getDoanhThuTheoDanhMuc(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                              @RequestParam(value = "currentDate") String currentDate){
        log.info("START: get current doanh thu theo danh muc");
        DoanhThuTheoDanhMucResponse response = baoCaoService.getDoanhThuTheoDanhMuc(cuaHangId, currentDate);
        log.info("END: get current doanh thu theo danh muc");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Doanh Thu Theo Danh Mục In Range Date ")
    @PostMapping("/doanh-thu-theo-danh-muc")
    public ResponseEntity<DoanhThuTheoDanhMucResponse> getDoanhThuTheoDanhMucInRangeDate(@RequestBody AbstractBaoCaoRequest request){
        log.info("START: get doanh thu theo danh muc from: " + request.getFromDate() + " to: " + request.getToDate());
        DoanhThuTheoDanhMucResponse response = baoCaoService.getDoanhThuTheoDanhMucInRangeDate(request);
        log.info("END: get doanh thu theo danh muc in range date");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Mặt Hàng Bán Chạy")
    @GetMapping("/mat-hang-ban-chay")
    public ResponseEntity<MatHangBanChayResponse> getMatHangBanChay(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                    @RequestParam(value = "currentDate") String currentDate){
        log.info("START: get current mat hang ban chay");
        MatHangBanChayResponse response = baoCaoService.getMatHangBanChay(cuaHangId, currentDate);
        log.info("END: get current mat hang ban chay");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Mặt Hàng Bán Chạy In Range Date")
    @PostMapping("/mat-hang-ban-chay")
    public ResponseEntity<MatHangBanChayResponse> getMatHangBanChayInRangeDate(@RequestBody AbstractBaoCaoRequest request){
        log.info("START: get mat hang ban chay from: " + request.getFromDate() + " to: " + request.getToDate());
        MatHangBanChayResponse response = baoCaoService.getMatHangBanChayInRangeDate(request);
        log.info("END: get mat hang ban chay in range date");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Báo Cáo Giảm Giá")
    @GetMapping("/bao-cao-giam-gia")
    public ResponseEntity<BaoCaoGiamGiaResponse> getBaoCaoGiamGia(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                  @RequestParam(value = "currentDate") String currentDate){
        log.info("START: get current bao cao giam gia");
        BaoCaoGiamGiaResponse response = baoCaoService.getBaoCaoGiamGia(cuaHangId, currentDate);
        log.info("END: get current bao cao giam gia");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Báo Cáo Giảm Giá In Range Date")
    @PostMapping("/bao-cao-giam-gia")
    public ResponseEntity<BaoCaoGiamGiaResponse> getBaoCaoGiamGiaInRangeDate(@RequestBody AbstractBaoCaoRequest request){
        log.info("START: get bao cao giam gia from: " + request.getFromDate() + " to: " + request.getToDate());
        BaoCaoGiamGiaResponse response = baoCaoService.getBaoCaoGiamGiaInRangeDate(request);
        log.info("END: get bao cao giam gia in range date");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Báo Cáo Doanh Thu Theo Nhân Viên")
    @GetMapping("/doanh-thu-theo-nhan-vien")
    public ResponseEntity<DoanhThuTheoNhanVienResponse> getBaoCaoDoanhThuTheoNhanVien(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                                      @RequestParam(value = "currentDate") String currentDate){
        log.info("START: get current bao cao doanh thu theo nhan vien");
        DoanhThuTheoNhanVienResponse response = baoCaoService.getBaoCaoDoanhThuTheoNhanVien(cuaHangId, currentDate);
        log.info("END: get current bao cao doanh thu theo nhan vien");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Báo Cáo Doanh Thu Theo Nhân Viên In Range Date")
    @PostMapping("/doanh-thu-theo-nhan-vien")
    public ResponseEntity<DoanhThuTheoNhanVienResponse> getBaoCaoDoanhThuTheoNhanVienInRangeDate(@RequestBody AbstractBaoCaoRequest request){
        log.info("START: get doanh thu theo nhan vien from: " + request.getFromDate() + " to: " + request.getToDate());
        DoanhThuTheoNhanVienResponse response = baoCaoService.getBaoCaoDoanhThuTheoNhanVienInRangeDate(request);
        log.info("END: get doanh thu theo nhan vien in range date");
        return ResponseEntity.ok(response);
    }
}
