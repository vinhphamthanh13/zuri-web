package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.MatHangRequest;
import com.ocha.boc.request.MatHangUpdateRequest;
import com.ocha.boc.response.MatHangResponse;
import com.ocha.boc.services.impl.MatHangService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MatHangController {

    @Autowired
    private MatHangService matHangService;

    /**
     * Create new Mat Hang
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create new Mat Hang", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/mat-hang")
    public ResponseEntity<MatHangResponse> createNewMatHang(@RequestBody MatHangRequest request) {
        MatHangResponse response = matHangService.createNewMatHang(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Update Mat Hang Infor
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Update Mat Hang", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/mat-hang")
    public ResponseEntity<MatHangResponse> updateMatHangInfor(@RequestBody MatHangUpdateRequest request) {
        MatHangResponse response = matHangService.updateMatHangInfor(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Find Mat Hang By Id
     *
     * @param cuaHangId
     * @param id
     * @return
     */
    @ApiOperation(value = "Find Mat Hang By Id", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/mat-hang/{cuaHangId}/{id}")
    public ResponseEntity<MatHangResponse> findMatHangById(@PathVariable("cuaHangId") String cuaHangId, @PathVariable("id") String id) {
        MatHangResponse response = matHangService.findMatHangById(cuaHangId, id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get All Mat Hang
     *
     * @param cuaHangId
     * @return
     */
    @ApiOperation(value = "Get All Mat Hang", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/mat-hang")
    public ResponseEntity<MatHangResponse> getAllMatHang(@RequestParam String cuaHangId) {
        MatHangResponse response = matHangService.getAllMatHang(cuaHangId);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete Mat Hang By Id
     *
     * @param cuaHangId
     * @param id
     * @return
     */
    @ApiOperation(value = "Delete Mat Hang By Id", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/mat-hang/{cuaHangId}/{id}")
    public ResponseEntity<AbstractResponse> deleteMatHangById(@PathVariable("cuaHangId") String cuaHangId, @PathVariable("id") String id) {
        MatHangResponse response = matHangService.deleteMatHangById(cuaHangId, id);
        return ResponseEntity.ok(response);
    }
}
