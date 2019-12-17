package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.base.DiscountFactory;
import com.ocha.boc.entity.Discount;
import com.ocha.boc.repository.DiscountRepository;
import com.ocha.boc.request.DiscountRequest;
import com.ocha.boc.request.DiscountUpdateRequest;
import com.ocha.boc.response.DiscountResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DiscountUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class DiscountService {

    private DiscountFactory discountFactory;

    @Autowired
    private DiscountRepository discountRepository;

    private DiscountUtils discountUtils;

    @Autowired
    public DiscountService(DiscountFactory discountFactory, DiscountUtils discountUtils) {
        this.discountFactory = discountFactory;
        this.discountUtils = discountUtils;
    }

    public DiscountResponse createNewDiscount(DiscountRequest request) {
        DiscountResponse response = new DiscountResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_DISCOUNT_FAIL);
        try {
            if (!Objects.isNull(request)) {
                if (discountUtils.isRequestValidate(request, response)) {
                    Discount discount = discountFactory.createDiscount(request);
                    if (!Objects.isNull(discount)) {
                        discountRepository.save(discount);
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(discount);
                    }
                }

            }
        } catch (Exception e) {
            log.error("Exception while creating new discount: ");
        }
        return response;
    }


    public AbstractResponse deleteById(String discountId) {
        AbstractResponse response = new AbstractResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.DELETE_DISCOUNT_BY_ID_FAIL);
        try {
            if (StringUtils.isNotEmpty(discountId)) {
                if (discountRepository.existsById(discountId)) {
                    Optional<Discount> optDiscount = discountRepository.findDiscountById(discountId);
                    discountRepository.delete(optDiscount.get());
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setSuccess(Boolean.TRUE);
                }
            }
        } catch (Exception e) {
            log.error("Error when delete discount: ", e);
        }
        return response;
    }

    public DiscountResponse updateDiscount(DiscountUpdateRequest request) {
        DiscountResponse response = new DiscountResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_DISCOUNT_FAIL);
        try {
            if (!Objects.isNull(request)) {
                if (discountUtils.isRequestValidate(request, response)) {
                    if (discountRepository.existsById(request.getDiscountId())) {
                        Optional<Discount> optDiscount = discountRepository.findDiscountById(request.getDiscountId());
                        Discount discount = discountFactory.updateDiscount(request, optDiscount.get());
                        discountRepository.save(discount);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setSuccess(Boolean.TRUE);
                        response.setObject(discount);
                    }
                }

            }
        } catch (Exception e) {
            log.error("Error when update discount: ", e);
        }
        return response;
    }

    public AbstractResponse test(DiscountRequest request) {
        AbstractResponse response = new AbstractResponse();
        Discount discount = discountFactory.createDiscount(request);
        discountRepository.save(discount);
        response.setObject(discount);
        return response;
    }

    public AbstractResponse testUpdate(DiscountUpdateRequest request) {
        AbstractResponse response = new AbstractResponse();
        Optional<Discount> optDiscount = discountRepository.findDiscountById(request.getDiscountId());
        Discount discount = discountFactory.updateDiscount(request, optDiscount.get());
        discountRepository.save(discount);
        response.setObject(discount);
        return response;
    }
}
