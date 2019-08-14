package com.ocha.boc.controllers;

import com.ocha.boc.request.GiayInRequest;
import com.ocha.boc.response.SystemConfigurationResponse;
import com.ocha.boc.services.impl.SystemConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @ApiOperation("Get all information about Giay In in the system")
    @GetMapping("/system/information")
    public ResponseEntity<SystemConfigurationResponse> getAllInformationAboutGiayIn() {
        log.info("START: Get all information about Giay In in the System");
        SystemConfigurationResponse response = systemConfigService.getAllInformationAboutGiayIn();
        log.info("END: Get all information about Giay In in the System");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Create new Giay In Information in the system")
    @PostMapping("/system/giay-in")
    public ResponseEntity<SystemConfigurationResponse> createNewGiayInInformation(@RequestBody GiayInRequest request) {
        log.info("START: create new GIẤY IN information in the system");
        SystemConfigurationResponse response = systemConfigService.createNewGiayInInformation(request);
        log.info("END: create new GIẤY IN information in the system");
        return ResponseEntity.ok(response);
    }

    @ApiOperation("Delete Giay In Information By Giay In Title")
    @DeleteMapping("/system/giay-in/{title}")
    public ResponseEntity<SystemConfigurationResponse> deleteGiayInByTitle(@PathVariable(value = "title") String title) {
        log.info("START: Delete Giay In with title name: " + title);
        SystemConfigurationResponse response = systemConfigService.deleteGiayInByTitle(title);
        log.info("END: Delete Giay In by title name");
        return ResponseEntity.ok(response);
    }

}
