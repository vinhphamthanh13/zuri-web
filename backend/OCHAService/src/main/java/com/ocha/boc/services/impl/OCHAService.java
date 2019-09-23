package com.ocha.boc.services.impl;

import com.ocha.boc.dto.CopyPrinterDTO;
import com.ocha.boc.entity.CopyPrinter;
import com.ocha.boc.enums.Region;
import com.ocha.boc.repository.CopyPrinterRepository;
import com.ocha.boc.request.GiayInRequest;
import com.ocha.boc.response.CopyPrinterResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OCHAService {

    @Autowired
    private CopyPrinterRepository copyPrinterRepository;

    public CopyPrinterResponse getAllCopyPrinters() {
        CopyPrinterResponse response = new CopyPrinterResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_COPY_PRINTER_SYSTEM_CONFIGURATION_FAIL);
        try {
            List<CopyPrinter> listCopyPrinter = copyPrinterRepository.findAll();
            if (CollectionUtils.isNotEmpty(listCopyPrinter)) {
                List<CopyPrinterDTO> result = listCopyPrinter.stream().map(copyPrinter -> {
                    return new CopyPrinterDTO(copyPrinter);
                }).collect(Collectors.toList());
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setObjects(result);
                response.setTotalResultCount((long) result.size());
            }
        } catch (Exception e) {
            log.error("Error when get all copy printers: ", e);
        }
        return response;
    }


    public CopyPrinterResponse createNewCopyPrinterInformation(GiayInRequest request) {
        CopyPrinterResponse response = new CopyPrinterResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_COPY_PRINTER_INFORMATION_FAIL);
        try {
            if (!Objects.isNull(request)) {
                CopyPrinter copyPrinter = CopyPrinter.builder()
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .region(Region.valueOf(request.getRegion().label))
                        .title(request.getTitle())
                        .build();
                copyPrinter.setCreatedDate(Date.from(Instant.now()).toString());
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setObject(new CopyPrinterDTO(copyPrinter));
            }
        } catch (Exception e) {
            log.error("Error when create new copy printer information: ", e);
        }
        return response;
    }

    public CopyPrinterResponse deleteCopyPrinterInforByTitle(String titleName) {
        CopyPrinterResponse response = new CopyPrinterResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_COPY_PRINTER_BY_TITLE_FAIL);
        try {
            if (StringUtils.isNotEmpty(titleName)) {
                if (copyPrinterRepository.existsByTitle(titleName)) {
                    Optional<CopyPrinter> optCopyPrinter = copyPrinterRepository.findByTitle(titleName);
                    copyPrinterRepository.delete(optCopyPrinter.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                }
            }
        } catch (Exception e) {
            log.error("Error when delete copy printer by title name: ", e);
        }
        return response;
    }

}
