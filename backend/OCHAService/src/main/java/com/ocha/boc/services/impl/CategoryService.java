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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                if (!categoryRepository.existsByName(request.getName())) {
                    Category category = new Category();
                    //Find max CategoryId Value
                    Optional<Category> temp = categoryRepository.findTopByOrderByCreatedDateDesc();
                    if (temp.isPresent()) {
                        int categoryIdMaxValue = Integer.parseInt(temp.get().getCategoryId());
                        category.setCategoryId(Integer.toString((categoryIdMaxValue + 1)));
                    } else {
                        //init first record in DB
                        category.setCategoryId(NUMBER_ONE);
                    }
                    category.setRestaurantId(request.getRestaurantId());
                    category.setAbbreviations(request.getAbbreviations());
                    category.setName(request.getName());
                    category.setCreatedDate(DateUtils.getCurrentDateAndTime());
                    categoryRepository.save(category);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new CategoryDTO(category));
                } else {
                    response.setMessage(CommonConstants.CATEGORY_IS_EXISTED);
                }
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
        }).orElse(new Category());
    }

    //@Cacheable(value = "danhmuc", key = "{#cuaHangId,#id}")
    public Category findCategoryByCategoryId(String id, String cuaHangId) {
        return categoryRepository.findCategoryByCategoryIdAndRestaurantId(id, cuaHangId).orElse(new Category());
    }

    public CategoryResponse getAllCategory(String cuaHangId) {
        CategoryResponse response = new CategoryResponse();
        try {
            response.setMessage(CommonConstants.GET_ALL_CATEGORY_FAIL);
            response.setSuccess(Boolean.FALSE);
            List<Category> listCategory = categoryRepository.findAllByRestaurantId(cuaHangId);
            if (CollectionUtils.isNotEmpty(listCategory)) {
                List<CategoryDTO> categoryDTOList = new ArrayList<>();
                for (Category category : listCategory) {
                    CategoryDTO temp = new CategoryDTO(category);
                    categoryDTOList.add(temp);
                }
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObjects(categoryDTOList);
                response.setTotalResultCount((long) categoryDTOList.size());
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
                if (categoryRepository.existsByCategoryIdAndRestaurantId(id, cuaHangId)) {
                    Optional<Category> optCategory = categoryRepository.findCategoryByCategoryIdAndRestaurantId(id, cuaHangId);
                    categoryRepository.delete(optCategory.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                } else {
                    response.setMessage(CommonConstants.CATEGORY_NAME_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when delete category: {}", e);
        }
        return response;
    }

}
