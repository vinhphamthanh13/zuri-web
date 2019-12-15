package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.CategoryDTO;
import com.ocha.boc.entity.Category;
import com.ocha.boc.enums.EntityType;
import com.ocha.boc.error.ResourceNotFoundException;
import com.ocha.boc.repository.CategoryRepository;
import com.ocha.boc.request.CategoryRequest;
import com.ocha.boc.request.CategoryUpdateRequest;
import com.ocha.boc.response.CategoryResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import com.ocha.boc.util.OchaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {

    private static final String NUMBER_ONE = "1";

    @Autowired
    private CategoryRepository categoryRepository;


    public CategoryResponse createNewCategory(CategoryRequest request) {
        CategoryResponse response = new CategoryResponse();
        try {
            response.setMessage(CommonConstants.CREATE_NEW_CATEGORY_FAIL);
            response.setSuccess(Boolean.FALSE);
            if (!Objects.isNull(request)) {
                if (categoryRepository.existsByName(request.getName())) {
                    response.setMessage(CommonConstants.CATEGORY_IS_EXISTED);
                    return response;
                }
                Category category = Category.builder()
                        .restaurantId(request.getRestaurantId())
                        .abbreviations(request.getAbbreviations())
                        .name(request.getName())
                        .categoryId("O-" + OchaUtils.generateUUIDCode().substring(0, 10))
                        .build();
                category.setCreatedDate(DateUtils.getCurrentDateAndTime());
                categoryRepository.save(category);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new CategoryDTO(category));
            }
        } catch (Exception e) {
            log.error("Error when create new category: ", e);
        }
        return response;
    }

    public CategoryResponse updateCategory(CategoryUpdateRequest request) {
        CategoryResponse response = new CategoryResponse();
        response.setMessage(CommonConstants.UPDATE_CATEGORY_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            categoryRepository.findCategoryByCategoryIdAndRestaurantId(request.getCategoryId(),
                    request.getRestaurantId()).map(category -> {
                if (StringUtils.isNotEmpty(request.getAbbreviations())) {
                    category.setAbbreviations(request.getAbbreviations());
                }
                if (StringUtils.isNotEmpty(request.getName())) {
                    category.setName(request.getName());
                }
                category.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                return categoryRepository.save(category);
            }).orElseThrow(() -> new ResourceNotFoundException(EntityType.CATEGORY.toString(),
                    request.getCategoryId() + " - " +
                            request.getRestaurantId(), request));
        } catch (Exception e) {
            log.error("Exception while updating category: ", e);
        }
        return response;
    }

    public CategoryResponse findCategoryById(String id, String restaurantId) {
        CategoryResponse response = new CategoryResponse();
        response.setMessage(CommonConstants.CATEGORY_NAME_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        try {
            Optional<Category> optCategory = categoryRepository.findCategoryByCategoryIdAndRestaurantId(id, restaurantId);
            if (optCategory.isPresent()) {
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setObject(new CategoryDTO(optCategory.get()));
            }
        } catch (Exception e) {
            log.error("Exception while finding category by id: ", e);
        }
        return response;
    }

    public CategoryResponse getAllCategory(String restaurantId) {
        CategoryResponse response = new CategoryResponse();
        response.setMessage(CommonConstants.GET_ALL_CATEGORY_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            List<Category> categories = categoryRepository.findAllByRestaurantId(restaurantId);
            if (CollectionUtils.isNotEmpty(categories)) {
                List<CategoryDTO> result = categories.stream().map(CategoryDTO::new).collect(Collectors.toList());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(result);
                response.setTotalResultCount((long) result.size());
            }
        } catch (Exception e) {
            log.error("Error when get all category: {}", e);
        }
        return response;
    }

    public AbstractResponse deleteCategoryByCategoryId(String id, String restaurantId) {
        AbstractResponse response = new AbstractResponse();
        try {
            response.setMessage(CommonConstants.DELETE_CATEGORY_BY_CATEGORY_ID_FAIL);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                if (!categoryRepository.existsByCategoryIdAndRestaurantId(id, restaurantId)) {
                    response.setMessage(CommonConstants.CATEGORY_NAME_IS_NULL);
                    return response;
                }
                Optional<Category> optCategory = categoryRepository.findCategoryByCategoryIdAndRestaurantId(id, restaurantId);
                categoryRepository.delete(optCategory.get());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            }
        } catch (Exception e) {
            log.error("Error when delete category: {}", e);
        }
        return response;
    }

}
