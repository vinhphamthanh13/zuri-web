package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.DanhMucRequest;
import com.ocha.boc.request.DanhMucUpdateRequest;
import com.ocha.boc.response.DanhMucResponse;
import com.ocha.boc.services.impl.DanhMucService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DanhMucController {

    @Autowired
    private DanhMucService danhMucService;

    /**
     * Create new Danh Muc
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create new Danh Muc", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/danh-muc")
    public ResponseEntity<DanhMucResponse> createNewDanhMuc(@RequestBody DanhMucRequest request) {
        DanhMucResponse response = danhMucService.createNewDanhMuc(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Update Danh Muc
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Update Danh Muc", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/danh-muc")
    public ResponseEntity<DanhMucResponse> updateDanhMuc(@RequestBody DanhMucUpdateRequest request) {
        DanhMucResponse response = danhMucService.updateDanhMuc(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Find Danh Muc By DanhMucId
     *
     * @param cuaHangId
     * @param id
     * @return
     */
    @ApiOperation(value = "Find Danh muc By DanhMucId", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/danh-muc/{cuaHangId}/{id}")
    public ResponseEntity<DanhMucResponse> findDanhMucById(@PathVariable("cuaHangId") String cuaHangId, @PathVariable("id") String id) {
        DanhMucResponse response = danhMucService.findDanhMucByDanhMucId(id, cuaHangId);
        return ResponseEntity.ok(response);
    }

    /**
     * Find All Danh Muc
     *
     * @param cuaHangId
     * @return
     */
    @ApiOperation(value = "Get all Danh Muc", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/danh-muc")
    public ResponseEntity<DanhMucResponse> getAllDanhMuc(@RequestParam String cuaHangId) {
        DanhMucResponse response = danhMucService.getAllDanhMuc(cuaHangId);
        return ResponseEntity.ok(response);
    }

    /**
     * delete Danh Muc By Id
     *
     * @param cuaHangId
     * @param id
     * @return
     */
    @ApiOperation(value = "Delete Danh Muc By DanhMucId", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/danh-muc/{cuaHangId}/{id}")
    public ResponseEntity<AbstractResponse> deleteDanhMucById(@PathVariable("cuaHangId") String cuaHangId, @PathVariable("id") String id) {
        AbstractResponse response = danhMucService.deleteDanhMucByDanhMucId(id, cuaHangId);
        return ResponseEntity.ok(response);
    }

}
