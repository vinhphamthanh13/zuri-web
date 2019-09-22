package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.GiayInRequest;
import com.ocha.boc.response.SystemConfigurationResponse;
import com.ocha.boc.services.impl.SystemConfigService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @ApiOperation(value = "Get all information about Giay In in the system", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/system/information")
    public ResponseEntity<SystemConfigurationResponse> getAllInformationAboutGiayIn() {
        log.info("START: Get all information about Giay In in the System");
        SystemConfigurationResponse response = systemConfigService.getAllInformationAboutGiayIn();
        log.info("END: Get all information about Giay In in the System");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Create new Giay In Information in the system", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/system/giay-in")
    public ResponseEntity<SystemConfigurationResponse> createNewGiayInInformation(@RequestBody GiayInRequest request) {
        log.info("START: create new GIẤY IN information in the system");
        SystemConfigurationResponse response = systemConfigService.createNewGiayInInformation(request);
        log.info("END: create new GIẤY IN information in the system");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Giay In Information By Giay In Title", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/system/giay-in/{title}")
    public ResponseEntity<SystemConfigurationResponse> deleteGiayInByTitle(@PathVariable(value = "title") String title) {
        log.info("START: Delete Giay In with title name: " + title);
        SystemConfigurationResponse response = systemConfigService.deleteGiayInByTitle(title);
        log.info("END: Delete Giay In by title name");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all information about Product Portfolio", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/system/productPortfolio")
    public ResponseEntity<AbstractResponse> getAllInformationAboutProductPortfolio() {
        log.info("START: Get all information about Product Portfolio");
        AbstractResponse response = systemConfigService.getAllInformationAboutProductPortfolio();
        log.info("END: get all information about Product Portfolio");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get all information about Business Models Type", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/system/businessModelsType")
    public ResponseEntity<AbstractResponse> getAllInformationAboutBusinessModelsType() {
        log.info("START: Get all information about Business Models Type");
        AbstractResponse response = systemConfigService.getAllInformationAboutBusinessModelsType();
        log.info("END: Get all information about Business Models Type");
        return ResponseEntity.ok(response);
    }

}
