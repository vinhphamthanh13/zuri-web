package com.ocha.boc.controllers;

import com.ocha.boc.request.KhuyenMaiRequest;
import com.ocha.boc.request.KhuyenMaiUpdateRequest;
import com.ocha.boc.response.KhuyenMaiResponse;
import com.ocha.boc.services.impl.KhuyenMaiService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KhuyenMaiController {

    @Autowired
    private KhuyenMaiService khuyenMaiService;

    @ApiOperation(value = "Create new Khuyen Mai")
    @PostMapping("/khuyen-mai")
    public ResponseEntity<KhuyenMaiResponse> createNewKhuyenMai(@RequestBody KhuyenMaiRequest request) {
        KhuyenMaiResponse response = khuyenMaiService.createNewKhuyenMai(request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all Khuyen Mai")
    @GetMapping("/khuyen-mai")
    public ResponseEntity<KhuyenMaiResponse> getAllKhuyenMai(@RequestParam String cuaHangId) {
        KhuyenMaiResponse response = khuyenMaiService.getAllKhuyenMai(cuaHangId);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get Khuyen Mai By KhuyenMaiId")
    @GetMapping("/khuyen-mai/{cuaHangId}/{id}")
    public ResponseEntity<KhuyenMaiResponse> getKhuyenMaiByKhuyenMaiId(@PathVariable("cuaHangId") String cuaHangId,@PathVariable("id") String id) {
        KhuyenMaiResponse response = khuyenMaiService.getKhuyenMaiByKhuyenMaiId(cuaHangId, id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Khuyen Mai By KhuyenMaiId")
    @DeleteMapping("/khuyen-mai/{cuaHangId}/{id}")
    public ResponseEntity<KhuyenMaiResponse> deleteKhuyenMaiByKhuyenMaiId(@PathVariable("cuaHangId") String cuaHangId, @PathVariable("id") String id) {
        KhuyenMaiResponse response = khuyenMaiService.deleteKhuyenMaiByKhuyenMaiId(cuaHangId , id);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Update Khuyen Mai")
    @PutMapping("/khuyen-mai")
    public ResponseEntity<KhuyenMaiResponse> updateKhuyenMai(@RequestBody KhuyenMaiUpdateRequest request){
        KhuyenMaiResponse response = khuyenMaiService.updateKhuyenMai(request);
        return ResponseEntity.ok(response);
    }

}
