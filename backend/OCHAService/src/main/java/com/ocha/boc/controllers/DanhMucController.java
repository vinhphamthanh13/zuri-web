package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.DanhMucDTO;
import com.ocha.boc.entity.DanhMuc;
import com.ocha.boc.request.DanhMucRequest;
import com.ocha.boc.request.DanhMucUpdateRequest;
import com.ocha.boc.response.DanhMucResponse;
import com.ocha.boc.services.impl.DanhMucService;
import com.ocha.boc.util.CommonConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
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
        log.info("[START]: create new Danh Muc");
        DanhMucResponse response = danhMucService.createNewDanhMuc(request);
        log.info("[END]: create new Danh Muc");
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
        log.info("[START]: update Danh Muc");
        DanhMucResponse response = new DanhMucResponse();
        response.setMessage(CommonConstants.UPDATE_DANH_MUC_FAIL);
        response.setSuccess(Boolean.FALSE);
        Optional<DanhMuc> danhMucOptional = Optional
                .ofNullable(danhMucService.updateDanhMuc(request));
        if(danhMucOptional.isPresent()){
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObject(new DanhMucDTO(danhMucOptional.get()));
        }
        log.info("[END]: update Danh Muc");
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
    public ResponseEntity<DanhMucResponse> findDanhMucById(@PathVariable("cuaHangId") String cuaHangId,
                                                           @PathVariable("id") String id) {
        log.info("[START]: Find Danh Muc By Id: " + id + " cuaHangId: " + cuaHangId);
        DanhMucResponse response = new DanhMucResponse();
        response.setMessage(CommonConstants.DANH_MUC_NAME_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        Optional<DanhMuc> danhMucOptional = Optional
                .ofNullable(danhMucService.findDanhMucByDanhMucId(id, cuaHangId));
        if (danhMucOptional.isPresent()) {
            response.setObject(new DanhMucDTO(danhMucOptional.get()));
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setSuccess(Boolean.TRUE);
        }
        log.info("[END]: Find Danh Muc By Id and CuaHangId");
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
        log.info("[START]: get all Danh Muc");
        DanhMucResponse response = danhMucService.getAllDanhMuc(cuaHangId);
        log.info("[END]: get all Danh Muc");
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
        log.info("[START]: delete Danh Muc");
        AbstractResponse response = danhMucService.deleteDanhMucByDanhMucId(id, cuaHangId);
        log.info("[END]: delete Danh Muc");
        return ResponseEntity.ok(response);
    }

}
