package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.GiamGiaRequest;
import com.ocha.boc.request.GiamGiaUpdateRequest;
import com.ocha.boc.response.GiamGiaResponse;
import com.ocha.boc.services.impl.GiamGiaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class GiamGiaController {

    @Autowired
    private GiamGiaService giamGiaService;

    /**
     * Create new Giam Gia
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create New Giam Gia", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/giam-gia")
    public ResponseEntity<GiamGiaResponse> createNewGiamGia(@RequestBody GiamGiaRequest request) {
        log.info("START: create new giam gia");
        GiamGiaResponse response = giamGiaService.createNewGiamGia(request);
        log.info("END: create new giam gia");
        return ResponseEntity.ok(response);
    }

    /**
     * delete Giam Gia By Id
     *
     * @param giamGiaId
     * @return
     */
    @ApiOperation(value = "Delete Giam Gia By GiamGiaId", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/giam-gia/{giamGiaId}")
    public ResponseEntity<AbstractResponse> deleteGiamGiaByGiamGiaId(@PathVariable(value = "giamGiaId") String giamGiaId) {
        log.info("START: delete giam gia by id: " + giamGiaId);
        AbstractResponse response = giamGiaService.deleteGiamGiaByGiamGiaId(giamGiaId);
        log.info("END: delete giam gia by id");
        return ResponseEntity.ok(response);
    }

    /**
     * Update Giam Gia Information
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Update Giam Gia", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/giam-gia")
    public ResponseEntity<GiamGiaResponse> updateGiamGiaByGiamGiaId(@RequestBody GiamGiaUpdateRequest request) {
        log.info("START: update giam gia");
        GiamGiaResponse response = giamGiaService.updateGiamGia(request);
        log.info("END: update giam gia");
        return ResponseEntity.ok(response);
    }

}
