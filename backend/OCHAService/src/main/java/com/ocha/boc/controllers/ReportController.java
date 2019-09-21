package com.ocha.boc.controllers;

import com.ocha.boc.request.AbstractReportRequest;
import com.ocha.boc.response.*;
import com.ocha.boc.services.impl.ReportService;
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
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Overview Revenue Report
     *
     * @param restaurantId
     * @return
     */
    @ApiOperation(value = "Overview Revenue Report", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/overviewRevenue")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OverviewRevenueResponse> getOverviewRevenue(@RequestParam(value = "restaurantId") String restaurantId) {
        log.info("START: Get Overview Revenue Report");
        OverviewRevenueResponse response = reportService.getOverviewRevenue(restaurantId);
        log.info("END: gGet Overview Revenue Report");
        return ResponseEntity.ok(response);
    }

    /**
     * Overview Revenue Report between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Get Overview Revenue Report In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/overviewRevenue")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<OverviewRevenueResponse> getOverviewRevenueInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: Get Overview Revenue Report From: " + request.getFromDate() + " to: " + request.getToDate());
        OverviewRevenueResponse response = reportService.getOverviewRevenueInRangeDate(request);
        log.info("END: Get Overview Revenue Report in range date");
        return ResponseEntity.ok(response);
    }

    /**
     * Revenue Category Report
     *
     * @param restaurantId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Get Revenue Category Report", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/revenueCategory")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RevenueCategoryResponse> getRevenueCategory(@RequestParam(value = "restaurantId") String restaurantId,
                                                                      @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: Get Revenue Category Report");
        RevenueCategoryResponse response = reportService.getRevenueCategory(restaurantId, currentDate);
        log.info("END: Get Revenue Category Report");
        return ResponseEntity.ok(response);
    }

    /**
     * Revenue Category Report between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Revenue Category Report In Range Date ", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/revenueCategory")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RevenueCategoryResponse> getRevenueCategoryInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: get revenue category from: " + request.getFromDate() + " to: " + request.getToDate());
        RevenueCategoryResponse response = reportService.getRevenueCategoryInRangeDate(request);
        log.info("END: get revenue category in range date");
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
        MatHangBanChayResponse response = reportService.getMatHangBanChay(cuaHangId, currentDate);
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
    public ResponseEntity<MatHangBanChayResponse> getMatHangBanChayInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: get mat hang ban chay from: " + request.getFromDate() + " to: " + request.getToDate());
        MatHangBanChayResponse response = reportService.getMatHangBanChayInRangeDate(request);
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
        BaoCaoGiamGiaResponse response = reportService.getBaoCaoGiamGia(cuaHangId, currentDate);
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
    public ResponseEntity<BaoCaoGiamGiaResponse> getBaoCaoGiamGiaInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: get bao cao giam gia from: " + request.getFromDate() + " to: " + request.getToDate());
        BaoCaoGiamGiaResponse response = reportService.getBaoCaoGiamGiaInRangeDate(request);
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
        DoanhThuTheoNhanVienResponse response = reportService.getBaoCaoDoanhThuTheoNhanVien(cuaHangId, currentDate);
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
    public ResponseEntity<DoanhThuTheoNhanVienResponse> getBaoCaoDoanhThuTheoNhanVienInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: get doanh thu theo nhan vien from: " + request.getFromDate() + " to: " + request.getToDate());
        DoanhThuTheoNhanVienResponse response = reportService.getBaoCaoDoanhThuTheoNhanVienInRangeDate(request);
        log.info("END: get doanh thu theo nhan vien in range date");
        return ResponseEntity.ok(response);
    }
}

