package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.BusinessModelsTypeDTO;
import com.ocha.boc.dto.ProductPortfolioDTO;
import com.ocha.boc.entity.BusinessModelsType;
import com.ocha.boc.entity.ProductPortfolio;
import com.ocha.boc.repository.BusinessModelsTypeRepository;
import com.ocha.boc.repository.ProductPortfolioRepository;
import com.ocha.boc.util.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SystemConfigService {


    @Autowired
    private ProductPortfolioRepository productPortfolioRepository;

    @Autowired
    private BusinessModelsTypeRepository businessModelsTypeRepository;


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
