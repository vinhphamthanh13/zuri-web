package com.ocha.boc.controllers;

import com.ocha.boc.request.AbstractBaoCaoRequest;
import com.ocha.boc.response.*;
import com.ocha.boc.services.impl.BaoCaoService;
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
public class BaoCaoController {

    @Autowired
    private BaoCaoService baoCaoService;

    /**
     * Report current Doanh Thu Tong Quan
     *
     * @param cuaHangId
     * @return
     */
    @ApiOperation(value = "Doanh Thu Tổng Quan", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/doanh-thu-tong-quan")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DoanhThuTongQuanResponse> getDoanhThuTongQuan(@RequestParam(value = "cuaHangId") String cuaHangId) {
        log.info("START: get current doanh thu tong quan");
        DoanhThuTongQuanResponse response = baoCaoService.getDoanhThuTongQuan(cuaHangId);
        log.info("END: get current doanh thu tong quan");
        return ResponseEntity.ok(response);
    }

    /**
     * Report Doanh Thu Tong Quan between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Get Doanh Thu Tong Quan In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/doanh-thu-tong-quan")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DoanhThuTongQuanResponse> getDoanhThuTongQuanInRangeDate(@RequestBody AbstractBaoCaoRequest request) {
        log.info("START: get doanh thu tong quan from: " + request.getFromDate() + " to: " + request.getToDate());
        DoanhThuTongQuanResponse response = baoCaoService.getDoanhThuTongQuanInRangeDate(request);
        log.info("END: get doanh thu tong quan in range date");
        return ResponseEntity.ok(response);
    }

    /**
     * Report current Doanh Thu Theo Danh Muc
     *
     * @param cuaHangId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Doanh Thu Theo Danh Mục", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/doanh-thu-theo-danh-muc")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DoanhThuTheoDanhMucResponse> getDoanhThuTheoDanhMuc(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                              @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: get current doanh thu theo danh muc");
        DoanhThuTheoDanhMucResponse response = baoCaoService.getDoanhThuTheoDanhMuc(cuaHangId, currentDate);
        log.info("END: get current doanh thu theo danh muc");
        return ResponseEntity.ok(response);
    }

    /**
     * Report Doanh Thu Theo Danh Muc between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Doanh Thu Theo Danh Mục In Range Date ", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/doanh-thu-theo-danh-muc")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DoanhThuTheoDanhMucResponse> getDoanhThuTheoDanhMucInRangeDate(@RequestBody AbstractBaoCaoRequest request) {
        log.info("START: get doanh thu theo danh muc from: " + request.getFromDate() + " to: " + request.getToDate());
        DoanhThuTheoDanhMucResponse response = baoCaoService.getDoanhThuTheoDanhMucInRangeDate(request);
        log.info("END: get doanh thu theo danh muc in range date");
        return ResponseEntity.ok(response);
    }

    /**
     * Report current Mat Hang Ban Chay
     *
     * @param cuaHangId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Mặt Hàng Bán Chạy", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/mat-hang-ban-chay")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MatHangBanChayResponse> getMatHangBanChay(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                    @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: get current mat hang ban chay");
        MatHangBanChayResponse response = baoCaoService.getMatHangBanChay(cuaHangId, currentDate);
        log.info("END: get current mat hang ban chay");
        return ResponseEntity.ok(response);
    }

    /**
     * Report Mat Hang Ban Chay between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Mặt Hàng Bán Chạy In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/mat-hang-ban-chay")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MatHangBanChayResponse> getMatHangBanChayInRangeDate(@RequestBody AbstractBaoCaoRequest request) {
        log.info("START: get mat hang ban chay from: " + request.getFromDate() + " to: " + request.getToDate());
        MatHangBanChayResponse response = baoCaoService.getMatHangBanChayInRangeDate(request);
        log.info("END: get mat hang ban chay in range date");
        return ResponseEntity.ok(response);
    }


    /**
     * Report current Giam Gia
     *
     * @param cuaHangId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Báo Cáo Giảm Giá", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/bao-cao-giam-gia")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaoCaoGiamGiaResponse> getBaoCaoGiamGia(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                  @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: get current bao cao giam gia");
        BaoCaoGiamGiaResponse response = baoCaoService.getBaoCaoGiamGia(cuaHangId, currentDate);
        log.info("END: get current bao cao giam gia");
        return ResponseEntity.ok(response);
    }

    /**
     * Report Giam Gia between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Báo Cáo Giảm Giá In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/bao-cao-giam-gia")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BaoCaoGiamGiaResponse> getBaoCaoGiamGiaInRangeDate(@RequestBody AbstractBaoCaoRequest request) {
        log.info("START: get bao cao giam gia from: " + request.getFromDate() + " to: " + request.getToDate());
        BaoCaoGiamGiaResponse response = baoCaoService.getBaoCaoGiamGiaInRangeDate(request);
        log.info("END: get bao cao giam gia in range date");
        return ResponseEntity.ok(response);
    }

    /**
     * Report Doanh Thu Theo Nhan Vien
     *
     * @param cuaHangId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Báo Cáo Doanh Thu Theo Nhân Viên", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/doanh-thu-theo-nhan-vien")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DoanhThuTheoNhanVienResponse> getBaoCaoDoanhThuTheoNhanVien(@RequestParam(value = "cuaHangId") String cuaHangId,
                                                                                      @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: get current bao cao doanh thu theo nhan vien");
        DoanhThuTheoNhanVienResponse response = baoCaoService.getBaoCaoDoanhThuTheoNhanVien(cuaHangId, currentDate);
        log.info("END: get current bao cao doanh thu theo nhan vien");
        return ResponseEntity.ok(response);
    }

    /**
     * Report Doanh Thu Theo Nhan Viet between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Báo Cáo Doanh Thu Theo Nhân Viên In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/doanh-thu-theo-nhan-vien")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DoanhThuTheoNhanVienResponse> getBaoCaoDoanhThuTheoNhanVienInRangeDate(@RequestBody AbstractBaoCaoRequest request) {
        log.info("START: get doanh thu theo nhan vien from: " + request.getFromDate() + " to: " + request.getToDate());
        DoanhThuTheoNhanVienResponse response = baoCaoService.getBaoCaoDoanhThuTheoNhanVienInRangeDate(request);
        log.info("END: get doanh thu theo nhan vien in range date");
        return ResponseEntity.ok(response);
    }
}

