package com.ocha.boc.util;

import com.ocha.boc.repository.CategoryRepository;
import com.ocha.boc.repository.RestaurantRepository;
import com.ocha.boc.request.DiscountRequest;
import com.ocha.boc.response.DiscountResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountUtils {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean isRequestValidate(DiscountRequest request, DiscountResponse response) {
        if (!restaurantRepository.existsById(request.getRestaurantId())) {
            response.setMessage(CommonConstants.RESTAURANT_IS_NOT_EXISTED);
            return false;
        }
        if (StringUtils.isNotEmpty(request.getCategoryId())) {
            if (!categoryRepository.existsById(request.getCategoryId())) {
                response.setMessage(CommonConstants.CATEGORY_NAME_IS_NULL);
                return false;
            }
        }
        return true;
    }
}
