package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.GiamGiaRequest;
import com.ocha.boc.request.GiamGiaUpdateRequest;
import com.ocha.boc.response.GiamGiaResponse;
import com.ocha.boc.services.impl.GiamGiaService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Create New Giam Gia")
    @PostMapping("/giam-gia")
    public ResponseEntity<GiamGiaResponse> createNewGiamGia(@RequestBody GiamGiaRequest request) {
        log.info("START: create new giam gia");
        GiamGiaResponse response = giamGiaService.createNewGiamGia(request);
        log.info("END: create new giam gia");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete Giam Gia By GiamGiaId")
    @DeleteMapping("/giam-gia/{giamGiaId}")
    public ResponseEntity<AbstractResponse> deleteGiamGiaByGiamGiaId(@PathVariable(value = "giamGiaId") String giamGiaId) {
        log.info("START: delete giam gia by id: " + giamGiaId);
        AbstractResponse response = giamGiaService.deleteGiamGiaByGiamGiaId(giamGiaId);
        log.info("END: delete giam gia by id");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Update Giam Gia")
    @PutMapping("/giam-gia")
    public ResponseEntity<GiamGiaResponse> updateGiamGiaByGiamGiaId(@RequestBody GiamGiaUpdateRequest request) {
        log.info("START: update giam gia");
        GiamGiaResponse response = giamGiaService.updateGiamGia(request);
        log.info("END: update giam gia");
        return ResponseEntity.ok(response);
    }

}
