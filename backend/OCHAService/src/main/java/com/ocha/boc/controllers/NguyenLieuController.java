package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.NguyenLieuDTO;
import com.ocha.boc.entity.NguyenLieu;
import com.ocha.boc.request.NguyenLieuRequest;
import com.ocha.boc.response.NguyenLieuResponse;
import com.ocha.boc.services.impl.NguyenLieuService;
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
public class NguyenLieuController {

    @Autowired
    private NguyenLieuService nguyenLieuService;

    /**
     * Create New Nguyen Lieu
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create new Nguyen Lieu", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> newNguyenLieu(@RequestBody NguyenLieuRequest request) {
        log.info("[START]: create new Nguyen Lieu");
        NguyenLieuResponse response = nguyenLieuService.newNguyenLieu(request);
        log.info("[END]: create new Nguyen Lieu");
        return ResponseEntity.ok(response);
    }

    /**
     * Update Nguyen Lieu Information
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "update NguyenLieu information", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> updateNguyenLieuInformation(@RequestBody NguyenLieuRequest request) {
        log.info("[START]: update Nguyen Lieu");
        NguyenLieuResponse response = new NguyenLieuResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_NGUYEN_LIEU_FAIL);
        Optional<NguyenLieu> nguyenLieuOptional = Optional
                .ofNullable(nguyenLieuService.updateNguyenLieuInformation(request));
        if (nguyenLieuOptional.isPresent()) {
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObject(new NguyenLieuDTO(nguyenLieuOptional.get()));
        } else {
            response.setMessage(CommonConstants.NGUYEN_LIEU_IS_NULL);
        }
        log.info("[END]: update Nguyen Lieu");
        return ResponseEntity.ok(response);
    }

    /**
     * Find Nguyen Lieu By Id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Find NguyenLieu by Id", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/nguyen-lieu/{id}")
    public ResponseEntity<NguyenLieuResponse> findNguyenLieuById(@PathVariable String id) {
        log.info("[START]: find Nguyen Lieu By Id");
        NguyenLieuResponse response = new NguyenLieuResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.NGUYEN_LIEU_IS_NULL);
        Optional<NguyenLieu> nguyenLieuOptional = Optional
                .ofNullable(nguyenLieuService.findNguyenLieuById(id));
        if(nguyenLieuOptional.isPresent()){
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setObject(new NguyenLieuDTO(nguyenLieuOptional.get()));
        }
        log.info("[END]: find Nguyen Lieu By Id");
        return ResponseEntity.ok(response);
    }

    /**
     * Get All Nguyen Lieu
     *
     * @return
     */
    @ApiOperation(value = "Get all Nguyen Lieu", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/nguyen-lieu")
    public ResponseEntity<NguyenLieuResponse> getAllNguyenLieu() {
        log.info("[START]: get all Nguyen Lieu");
        NguyenLieuResponse response = nguyenLieuService.getAllNguyenLieu();
        log.info("[END]: get all Nguyen Lieu");
        return ResponseEntity.ok(response);
    }

    /**
     * Delete Nguyen Lieu By Id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "Delete Nguyen Lieu By Id", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/nguyen-lieu/{id}")
    public ResponseEntity<AbstractResponse> deleteNguyenLieuById(@PathVariable String id) {
        log.info("[START]: Delete Nguyen Lieu");
        AbstractResponse response = nguyenLieuService.deleteNguyenLieuById(id);
        log.info("[END]: Delete Nguyen Lieu");
        return ResponseEntity.ok(response);
    }

}
