package com.ocha.boc.base;

import com.ocha.boc.entity.Discount;
import com.ocha.boc.entity.DiscountByCategory;
import com.ocha.boc.entity.RegularDiscountAmount;
import com.ocha.boc.entity.RegularPercentageDiscount;
import com.ocha.boc.request.DiscountRequest;
import com.ocha.boc.request.DiscountUpdateRequest;
import com.ocha.boc.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DiscountFactory {

    public Discount createDiscount(DiscountRequest request) {
        Discount discount = null;
        switch (request.getDiscountType()) {
            case GIẢM_GIÁ_THEO_DANH_MỤC:
                discount = new DiscountByCategory(request.getCategoryId(), request.getName(),
                        request.getRestaurantId(), request.getPercentage());
                break;
            case GIẢM_GIÁ_THÔNG_THƯỜNG:
                if (!Objects.isNull(request.getDiscountAmount())) {
                    discount = new RegularDiscountAmount(request.getName(), request.getRestaurantId(), request.getDiscountAmount());
                } else if (!Objects.isNull(request.getPercentage())) {
                    discount = new RegularPercentageDiscount(request.getName(), request.getRestaurantId(), request.getPercentage());
                }
                break;
            default:
                throw new UnsupportedOperationException("Unsupported discount type!");
        }
        return discount;
    }

    public Discount updateDiscount(DiscountUpdateRequest request, Discount discount) {
        Discount newDiscount = null;
        switch (request.getDiscountType()) {
            case GIẢM_GIÁ_THÔNG_THƯỜNG:
                if (!Objects.isNull(request.getDiscountAmount())) {
                    newDiscount = new RegularDiscountAmount(request.getName(),
                            request.getRestaurantId(), request.getDiscountAmount());
                    newDiscount.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                    newDiscount.setId(discount.getId());
                } else if (!Objects.isNull(request.getPercentage())) {
                    newDiscount = new RegularPercentageDiscount(request.getName(),
                            request.getRestaurantId(), request.getPercentage());
                }
                break;
            case GIẢM_GIÁ_THEO_DANH_MỤC:
                newDiscount = new DiscountByCategory(request.getCategoryId(), request.getName(),
                        request.getRestaurantId(), request.getPercentage());
                newDiscount.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                newDiscount.setId(discount.getId());
                break;
            default:
                throw new UnsupportedOperationException("Unsupported discount type!");
        }
        return newDiscount;
    }
}
