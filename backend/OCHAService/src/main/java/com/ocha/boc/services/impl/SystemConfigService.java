package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.BusinessModelsTypeDTO;
import com.ocha.boc.dto.ProductPortfolioDTO;
import com.ocha.boc.dto.SystemConfigDTO;
import com.ocha.boc.entity.BusinessModelsType;
import com.ocha.boc.entity.GiayIn;
import com.ocha.boc.entity.ProductPortfolio;
import com.ocha.boc.entity.SystemConfig;
import com.ocha.boc.repository.BusinessModelsTypeRepository;
import com.ocha.boc.repository.ProductPortfolioRepository;
import com.ocha.boc.repository.SystemConfigRepository;
import com.ocha.boc.request.GiayInRequest;
import com.ocha.boc.response.SystemConfigurationResponse;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SystemConfigService {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private ProductPortfolioRepository productPortfolioRepository;

    @Autowired
    private BusinessModelsTypeRepository businessModelsTypeRepository;

    public SystemConfigurationResponse getAllInformationAboutGiayIn() {
        SystemConfigurationResponse response = new SystemConfigurationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_GIAY_IN_SYSTEM_CONFIGURATION_FAIL);
        try {
            List<GiayIn> listGiayIn = systemConfigRepository.findAllByListGiayInConfiguration();
            if (CollectionUtils.isNotEmpty(listGiayIn)) {
                SystemConfig systemConfig = new SystemConfig();
                systemConfig.setListGiayInConfiguration(listGiayIn);
                SystemConfigDTO systemConfigDTO = new SystemConfigDTO(systemConfig);
                response.setObject(systemConfigDTO);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            }
        } catch (Exception e) {
            log.error("Error when getAllSystemConfigurationInformation: ", e);
        }
        return response;
    }


    public SystemConfigurationResponse createNewGiayInInformation(GiayInRequest request) {
        SystemConfigurationResponse response = new SystemConfigurationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_GIAY_IN_INFORMATION_FAIL);
        try {
            if (!Objects.isNull(request)) {
                GiayIn giayIn = new GiayIn();
                giayIn.setDescription(request.getDescription());
                giayIn.setPrice(request.getPrice());
                giayIn.setRegion(request.getRegion());
                giayIn.setTitle(request.getTitle());
                List<GiayIn> listGiayIn = systemConfigRepository.findAllByListGiayInConfiguration();
                if (listGiayIn == null) {
                    listGiayIn = new ArrayList<GiayIn>();
                }
                listGiayIn.add(giayIn);
                SystemConfig systemConfig = new SystemConfig();
                systemConfig.setListGiayInConfiguration(listGiayIn);
                SystemConfigDTO systemConfigDTO = new SystemConfigDTO(systemConfig);
                response.setObject(systemConfigDTO);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        } catch (Exception e) {
            log.error("Error when createNewGiayInInformation: ", e);
        }
        return response;
    }

    public SystemConfigurationResponse deleteGiayInByTitle(String titleName) {
        SystemConfigurationResponse response = new SystemConfigurationResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_GIAY_IN_BY_TITLE_FAIL);
        try {
            if (StringUtils.isNotEmpty(titleName)) {
                List<GiayIn> listGiayIn = systemConfigRepository.findAllByListGiayInConfiguration();
                if (CollectionUtils.isNotEmpty(listGiayIn)) {
                    int index = findIndexGiayInByTitle(titleName, listGiayIn);
                    listGiayIn.remove(index);
                    SystemConfig systemConfig = new SystemConfig();
                    systemConfig.setListGiayInConfiguration(listGiayIn);
                    SystemConfigDTO systemConfigDTO = new SystemConfigDTO(systemConfig);
                    response.setObject(systemConfigDTO);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when deleteGiayInByTitle: ", e);
        }
        return response;
    }

    private int findIndexGiayInByTitle(String title, List<GiayIn> listGiayIn) {
        int index = 0;
        if (listGiayIn.stream().anyMatch(tmp -> tmp.getTitle().equalsIgnoreCase(title))) {
            index = IntStream.range(0, listGiayIn.size()).filter(i -> title.equalsIgnoreCase(listGiayIn.get(i).getTitle())).findFirst().getAsInt();
        }
        return index;
    }

    public AbstractResponse getAllInformationAboutProductPortfolio() {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_PRODUCT_PORTFOLIO_FAIL);
        try {
            List<ProductPortfolio> results = productPortfolioRepository.findAll();
            if (CollectionUtils.isEmpty(results)) {
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.GET_ALL_PRODUCT_PORTFOLIO_DOESNT_EXISTED);
                return response;
            }
            List<ProductPortfolioDTO> productPortfolioDTOList = new ArrayList<ProductPortfolioDTO>();
            for (ProductPortfolio productPortfolio : results) {
                ProductPortfolioDTO temp = new ProductPortfolioDTO(productPortfolio);
                productPortfolioDTOList.add(temp);
            }
            response.setObjects(productPortfolioDTOList);
            response.setTotalResultCount((long) productPortfolioDTOList.size());
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
        } catch (Exception e) {
            log.error("Exception while get all information about Product Portfolio: ", e);
        }
        return response;
    }


    public AbstractResponse getAllInformationAboutBusinessModelsType() {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.GET_ALL_BUSINESS_MODELS_TYPE_FAIL);
        try {
            List<BusinessModelsType> results = businessModelsTypeRepository.findAll();
            if (CollectionUtils.isEmpty(results)) {
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.GET_ALL_BUSINESS_MODELS_TYPE_DOESNT_EXISTED);
            }
            List<BusinessModelsTypeDTO> businessModelsTypeDTOList = new ArrayList<BusinessModelsTypeDTO>();
            for (BusinessModelsType businessModelsType : results) {
                BusinessModelsTypeDTO temp = new BusinessModelsTypeDTO(businessModelsType);
                businessModelsTypeDTOList.add(temp);
            }
            response.setObjects(businessModelsTypeDTOList);
            response.setTotalResultCount((long) businessModelsTypeDTOList.size());
            response.setSuccess(Boolean.TRUE);
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
        } catch (Exception e) {
            log.error("Exception while get all information about Business Models Type: ", e);
        }
        return response;
    }
}
