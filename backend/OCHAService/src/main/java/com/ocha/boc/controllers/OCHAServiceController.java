package com.ocha.boc.controllers;

import com.ocha.boc.request.CopyPrintersRequest;
import com.ocha.boc.response.CopyPrinterResponse;
import com.ocha.boc.services.impl.OCHAService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class OCHAServiceController {

    @Autowired
    private OCHAService ochaService;

    @ApiOperation(value = "Get all information about Copy Printer in the system", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/system/copyPrinters")
    public ResponseEntity<CopyPrinterResponse> getAllCopyPrinters() {
        log.info("START: Get all information about Copy Printer in the System");
        CopyPrinterResponse response = ochaService.getAllCopyPrinters();
        log.info("END: Get all information about Copy Printer in the System");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Create new Copy Printer Information in the system", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/system/copyPrinters")
    public ResponseEntity<CopyPrinterResponse> createNewCopyPrinterInformation(@RequestBody CopyPrintersRequest request) {
        log.info("START: create new Copy Printer information in the system");
        CopyPrinterResponse response = ochaService.createNewCopyPrinterInformation(request);
        log.info("END: create new Copy Printer information in the system");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Delete Copy Printer Information By Title", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/system/copyPrinters/{title}")
    public ResponseEntity<CopyPrinterResponse> deleteCopyPrinterInforByTitle(@PathVariable(value = "title") String title) {
        log.info("START: Delete Copy Printer Information with title name: " + title);
        CopyPrinterResponse response = ochaService.deleteCopyPrinterInforByTitle(title);
        log.info("END: Delete Copy Printer Information by title name");
        return ResponseEntity.ok(response);
    }

}
