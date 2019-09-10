package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.MathangDTO;
import com.ocha.boc.entity.MatHang;
import com.ocha.boc.request.MatHangListRequest;
import com.ocha.boc.request.MatHangRequest;
import com.ocha.boc.request.MatHangUpdateRequest;
import com.ocha.boc.response.MatHangResponse;
import com.ocha.boc.services.impl.MatHangService;
import com.ocha.boc.util.CommonConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
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
        log.info("[START]: create new Mat Hang");
        MatHangResponse response = matHangService.createNewMatHang(request);
        log.info("[END]: create new Mat Hang");
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
        log.info("[START]: update Mat Hang");
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.UPDATE_MAT_HANG_FAIL);
        response.setSuccess(Boolean.FALSE);
        Optional<MatHang> matHangOptional = Optional
                .ofNullable(matHangService.updateMatHangInfor(request));
        if (matHangOptional.isPresent()) {
            if (!matHangOptional.get().checkObjectEmptyData()) {
                response.setObject(new MathangDTO(matHangOptional.get()));
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            } else {
                response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
            }
        }
        log.info("[END]: update Mat Hang");
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
    public ResponseEntity<MatHangResponse> findMatHangById(@PathVariable("cuaHangId") String cuaHangId,
                                                           @PathVariable("id") String id) {
        log.info("[START]: find Mat Hang by Id: " + id + " - cuaHangId: " + cuaHangId);
        MatHangResponse response = new MatHangResponse();
        response.setMessage(CommonConstants.MAT_HANG_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        Optional<MatHang> matHangOptional = Optional
                .ofNullable(matHangService.findMatHangById(cuaHangId, id));
        if (matHangOptional.isPresent()) {
            if (!matHangOptional.get().checkObjectEmptyData()) {
                response.setObject(new MathangDTO(matHangOptional.get()));
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        }
        log.info("[END]: find Mat Hang by Id");
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
        log.info("[START]: get all Mat Hang");
        MatHangResponse response = matHangService.getAllMatHang(cuaHangId);
        log.info("[END]: get all Mat Hang");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Test API")
    @GetMapping("/mat-hang/search")
    public ResponseEntity<MatHangResponse> test( MatHangListRequest request){
        MatHangResponse response = new MatHangResponse();
        Page<MatHang> temp = matHangService.test(request);
        List<MatHang> tempList = temp.getContent();
        List<MathangDTO> result = new ArrayList<MathangDTO>();
        for(MatHang matHang: tempList){
            result.add(new MathangDTO(matHang));
        }
        response.setObjects(result);
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
    public ResponseEntity<AbstractResponse> deleteMatHangById(@PathVariable("cuaHangId") String cuaHangId,
                                                              @PathVariable("id") String id) {
        log.info("[START]: delete Mat Hang By Id");
        MatHangResponse response = matHangService.deleteMatHangById(cuaHangId, id);
        log.info("[END]: delete Mat Hang By Id");
        return ResponseEntity.ok(response);
    }
}
