package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.services.impl.SystemConfigService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;


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
