package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.DiscountDTO;
import com.ocha.boc.entity.Discount;
import com.ocha.boc.enums.DiscountType;
import com.ocha.boc.repository.DiscountRepository;
import com.ocha.boc.request.DiscountRequest;
import com.ocha.boc.request.DiscountUpdateRequest;
import com.ocha.boc.response.DiscountResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    public DiscountResponse createNewDiscount(DiscountRequest request) {
        DiscountResponse response = new DiscountResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.CREATE_NEW_DISCOUNT_FAIL);
        try {
            if (!Objects.isNull(request)) {
                Discount discount = Discount.builder()
                        .restaurantId(request.getRestaurantId())
                        .categoryId(StringUtils.isNotEmpty(request.getCategoryId()) ? request.getCategoryId() : StringUtils.EMPTY)
                        .discountAmount(!Objects.isNull(request.getDiscountAmount()) ? request.getDiscountAmount() : BigDecimal.ZERO)
                        .percentage(!Objects.isNull(request.getPercentage()) ? request.getPercentage() : BigDecimal.ZERO)
                        .createdDate(DateUtils.getCurrentDateAndTime())
                        .discountType(request.getDiscountType())
                        .build();
                discountRepository.save(discount);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new DiscountDTO(discount));
            }
        } catch (Exception e) {
            log.error("Error when create new discount: ", e);
        }
        return response;
    }

    public AbstractResponse deleteDiscountByDiscountId(String discountId) {
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

    //TODO: refactor code - apply new way to solve the multiple cases discount type
    public DiscountResponse updateDiscount(DiscountUpdateRequest request) {
        DiscountResponse response = new DiscountResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_DISCOUNT_FAIL);
        try {
            if (request != null) {
                if (StringUtils.isNotEmpty(request.getDiscountId())) {
                    if (discountRepository.existsById(request.getDiscountId())) {
                        Optional<Discount> optDiscount = discountRepository.findDiscountById(request.getDiscountId());
                        optDiscount.get().setName(request.getName());
                        optDiscount.get().setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                        if (request.getDiscountType().label.equalsIgnoreCase(DiscountType.GIẢM_GIÁ_THEO_DANH_MỤC.label)) {
                            if (request.getPercentage() != null) {
                                optDiscount.get().setPercentage(request.getPercentage());
                            }
                            if (StringUtils.isNotEmpty(request.getCategoryId())) {
                                optDiscount.get().setCategoryId(request.getCategoryId());
                            }
                        } else if (request.getDiscountType().label.equalsIgnoreCase(DiscountType.GIẢM_GIÁ_THÔNG_THƯỜNG.label)) {
                            if (request.getPercentage() != null) {
                                optDiscount.get().setPercentage(request.getPercentage());
                                optDiscount.get().setDiscountAmount(null);
                            }
                            if (request.getDiscountAmount() != null) {
                                optDiscount.get().setDiscountAmount(request.getDiscountAmount());
                                optDiscount.get().setPercentage(null);
                            }
                        }
                        optDiscount.get().setDiscountType(request.getDiscountType());
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setSuccess(Boolean.TRUE);
                        response.setObject(new DiscountDTO(optDiscount.get()));
                        discountRepository.save(optDiscount.get());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when update discount: ", e);
        }
        return response;
    }
}
