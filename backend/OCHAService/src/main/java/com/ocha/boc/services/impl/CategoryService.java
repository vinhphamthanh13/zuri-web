package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.CategoryDTO;
import com.ocha.boc.entity.Category;
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
                        .createdDate(DateUtils.getCurrentDateAndTime())
                        .categoryId("O-" + OchaUtils.generateUUIDCode().substring(0, 10))
                        .build();
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

    //@CachePut(value = "danhmuc", key = "{#request.cuaHangId, #request.danhMucId}")
    public Category updateCategory(CategoryUpdateRequest request) {
        return categoryRepository.findCategoryByCategoryIdAndRestaurantId(request.getCategoryId(),
                request.getRestaurantId()).map(danhMuc -> {
            if (StringUtils.isNotEmpty(request.getAbbreviations())) {
                danhMuc.setAbbreviations(request.getAbbreviations());
            }
            if (StringUtils.isNotEmpty(request.getName())) {
                danhMuc.setName(request.getName());
            }
            danhMuc.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
            return categoryRepository.save(danhMuc);
        }).orElse(null);
    }

    //@Cacheable(value = "danhmuc", key = "{#cuaHangId,#id}")
    public Category findCategoryByCategoryId(String id, String cuaHangId) {
        return categoryRepository.findCategoryByCategoryIdAndRestaurantId(id, cuaHangId).orElse(null);
    }

    public CategoryResponse getAllCategory(String cuaHangId) {
        CategoryResponse response = new CategoryResponse();
        try {
            response.setMessage(CommonConstants.GET_ALL_CATEGORY_FAIL);
            response.setSuccess(Boolean.FALSE);
            List<Category> categories = categoryRepository.findAllByRestaurantId(cuaHangId);
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

    //@CacheEvict(value = "danhmuc", key = "{#cuaHangId,#id}")
    public AbstractResponse deleteCategoryByCategoryId(String id, String cuaHangId) {
        AbstractResponse response = new AbstractResponse();
        try {
            response.setMessage(CommonConstants.DELETE_CATEGORY_BY_CATEGORY_ID_FAIL);
            response.setSuccess(Boolean.FALSE);
            if (StringUtils.isNotEmpty(id)) {
                if (!categoryRepository.existsByCategoryIdAndRestaurantId(id, cuaHangId)) {
                    response.setMessage(CommonConstants.CATEGORY_NAME_IS_NULL);
                    return response;
                }
                Optional<Category> optCategory = categoryRepository.findCategoryByCategoryIdAndRestaurantId(id, cuaHangId);
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
