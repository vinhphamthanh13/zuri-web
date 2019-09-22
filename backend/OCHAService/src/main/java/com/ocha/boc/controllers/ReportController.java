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
     * Hot Deals Products Report: Mat hang ban chay
     *
     * @param restaurantId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Hot Deals Products Report", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/hotDealsProducts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotDealsProductsResponse> getHotDealsProducts(@RequestParam(value = "restaurantId") String restaurantId,
                                                                        @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: get current hot deals products");
        HotDealsProductsResponse response = reportService.getHotDealsProducts(restaurantId, currentDate);
        log.info("END: get current hot deals products");
        return ResponseEntity.ok(response);
    }

    /**
     * Hot Deals Products Report between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Hot Deals Products Report In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/hotDealsProducts")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HotDealsProductsResponse> getHotDealsProductsInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: get hot deals products from: " + request.getFromDate() + " to: " + request.getToDate());
        HotDealsProductsResponse response = reportService.getHotDealsProductsInRangeDate(request);
        log.info("END: get hot deals products in range date");
        return ResponseEntity.ok(response);
    }


    /**
     * Discount Report: Báo Cáo Giảm Giá
     *
     * @param restaurantId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Discount Report", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/discountReport")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DiscountReportResponse> getDiscountReport(@RequestParam(value = "restaurantId") String restaurantId,
                                                                    @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: get current Discount Report");
        DiscountReportResponse response = reportService.getDiscountReport(restaurantId, currentDate);
        log.info("END: get current Discount Report");
        return ResponseEntity.ok(response);
    }

    /**
     * Discount Report between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Discount Report In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/discountReport")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DiscountReportResponse> getDiscountReportInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: get Discount Report from: " + request.getFromDate() + " to: " + request.getToDate());
        DiscountReportResponse response = reportService.getDiscountReportInRangeDate(request);
        log.info("END: get Discount Report in range date");
        return ResponseEntity.ok(response);
    }

    /**
     * Employees Revenue Report: Doanh thu theo nhân viên
     *
     * @param restaurantId
     * @param currentDate
     * @return
     */
    @ApiOperation(value = "Employees Revenue Report", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/employeesRevenue")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeesRevenueResponse> getEmployeesRevenueReport(@RequestParam(value = "restaurantId") String restaurantId,
                                                                              @RequestParam(value = "currentDate") String currentDate) {
        log.info("START: get current Employees Revenue Report");
        EmployeesRevenueResponse response = reportService.getEmployeesRevenueReport(restaurantId, currentDate);
        log.info("END: get current Employees Revenue Report");
        return ResponseEntity.ok(response);
    }

    /**
     * Employees Revenue Report between two days
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Employees Revenue Report In Range Date", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/employeesRevenue")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<EmployeesRevenueResponse> getEmployeesRevenueReportInRangeDate(@RequestBody AbstractReportRequest request) {
        log.info("START: get Employees Revenue Report from: " + request.getFromDate() + " to: " + request.getToDate());
        EmployeesRevenueResponse response = reportService.getEmployeesRevenueReportInRangeDate(request);
        log.info("END: get Employees Revenue Report in range date");
        return ResponseEntity.ok(response);
    }
}

