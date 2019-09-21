package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.CategoryDTO;
import com.ocha.boc.entity.Category;
import com.ocha.boc.request.CategoryRequest;
import com.ocha.boc.request.CategoryUpdateRequest;
import com.ocha.boc.response.CategoryResponse;
import com.ocha.boc.services.impl.CategoryService;
import com.ocha.boc.util.CommonConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Create new Category
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create new Category", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> createNewCategory(@RequestBody @Valid CategoryRequest request) {
        log.info("[START]: create new Category");
        CategoryResponse response = categoryService.createNewCategory(request);
        log.info("[END]: create new Category");
        return ResponseEntity.ok(response);
    }

    /**
     * Update Category
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Update Category", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/categories")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid CategoryUpdateRequest request) {
        log.info("[START]: update Category");
        CategoryResponse response = new CategoryResponse();
        response.setMessage(CommonConstants.UPDATE_CATEGORY_FAIL);
        response.setSuccess(Boolean.FALSE);
        Optional<Category> optCategory = Optional
                .ofNullable(categoryService.updateCategory(request));
        if (optCategory.isPresent()) {
            if (!optCategory.get().checkObjectEmptyData()) {
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new CategoryDTO(optCategory.get()));
            }
        }
        log.info("[END]: update Category");
        return ResponseEntity.ok(response);
    }

    /**
     * Find Category By CategoryId
     *
     * @param restaurantId
     * @param id
     * @return
     */
    @ApiOperation(value = "Find Category By CategoryId", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/categories/{restaurantId}/{id}")
    public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable("restaurantId") String restaurantId,
                                                            @PathVariable("id") String id) {
        log.info("[START]: Find Category By Id: " + id + " restaurantId: " + restaurantId);
        CategoryResponse response = new CategoryResponse();
        response.setMessage(CommonConstants.CATEGORY_NAME_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        Optional<Category> optCategory = Optional
                .ofNullable(categoryService.findCategoryByCategoryId(id, restaurantId));
        if (optCategory.isPresent()) {
            if (!optCategory.get().checkObjectEmptyData()) {
                response.setObject(new CategoryDTO(optCategory.get()));
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        }
        log.info("[END]: Find Category By Id and restaurantId");
        return ResponseEntity.ok(response);
    }

    /**
     * Find All Category
     *
     * @param restaurantId
     * @return
     */
    @ApiOperation(value = "Get all Category", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/categories")
    public ResponseEntity<CategoryResponse> getAllCategory(@RequestParam String restaurantId) {
        log.info("[START]: get all Category");
        CategoryResponse response = categoryService.getAllCategory(restaurantId);
        log.info("[END]: get all Category");
        return ResponseEntity.ok(response);
    }

    /**
     * delete Category By Id
     *
     * @param restaurantId
     * @param id
     * @return
     */
    @ApiOperation(value = "Delete Category By CategoryId", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/categories/{restaurant}/{id}")
    public ResponseEntity<AbstractResponse> deleteCategoryById(@PathVariable("restaurantId") String restaurantId, @PathVariable("id") String id) {
        log.info("[START]: delete Category");
        AbstractResponse response = categoryService.deleteCategoryByCategoryId(id, restaurantId);
        log.info("[END]: delete Category");
        return ResponseEntity.ok(response);
    }

}
