package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.ProductDTO;
import com.ocha.boc.entity.Product;
import com.ocha.boc.request.ProductListRequest;
import com.ocha.boc.request.ProductRequest;
import com.ocha.boc.request.ProductUpdateRequest;
import com.ocha.boc.response.ProductResponse;
import com.ocha.boc.services.impl.ProductService;
import com.ocha.boc.util.CommonConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Create new Product
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Create new Product", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createNewProduct(@Valid @RequestBody ProductRequest request) {
        log.info("[START]: create new Product");
        ProductResponse response = productService.createNewProduct(request);
        log.info("[END]: create new Product");
        return ResponseEntity.ok(response);
    }

    /**
     * Update Product Infor
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "Update Product", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/products")
    public ResponseEntity<ProductResponse> updateProductInfor(@NotNull @RequestBody ProductUpdateRequest request) {
        log.info("[START]: update Product");
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.UPDATE_PRODUCT_FAIL);
        response.setSuccess(Boolean.FALSE);
        Optional<Product> optProduct = Optional
                .ofNullable(productService.updateProductInfor(request));
        if (!optProduct.isPresent()) {
            response.setMessage(CommonConstants.PRODUCT_IS_NULL);
            return ResponseEntity.ok(response);
        }
        response.setObject(new ProductDTO(optProduct.get()));
        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
        response.setSuccess(Boolean.TRUE);
        log.info("[END]: update Mat Hang");
        return ResponseEntity.ok(response);
    }

    /**
     * Find Product By Id
     *
     * @param restaurantId
     * @param id
     * @return
     */
    @ApiOperation(value = "Find Product By Id", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/products/{restaurantId}/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable("restaurantId") String restaurantId,
                                                           @PathVariable("id") String id) {
        log.info("[START]: find products by Id: " + id + " - restaurantId: " + restaurantId);
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.PRODUCT_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        Optional<Product> optProduct = Optional
                .ofNullable(productService.findProductById(restaurantId, id));
        if (optProduct.isPresent()) {
            response.setObject(new ProductDTO(optProduct.get()));
            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            response.setSuccess(Boolean.TRUE);
        }
        log.info("[END]: find products by Id");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get All Products", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/products/search")
    public ResponseEntity<ProductResponse> getAllProducts(ProductListRequest request) {
        log.info("[START]: get all products");
        ProductResponse response = new ProductResponse();
        Page<Product> productsPage = productService.search(request);
        List<ProductDTO> result = productsPage.getContent().stream().map(ProductDTO::new).collect(Collectors.toList());
        response.setObjects(result);
        response.setSuccess(Boolean.TRUE);
        response.setTotalResultCount((long) result.size());
        log.info("[END]: get all products");
        return ResponseEntity.ok(response);
    }

    /**
     * Delete product By Id
     *
     * @param restaurantId
     * @param id
     * @return
     */
    @ApiOperation(value = "Delete Product By Id", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/products/{restaurantId}/{id}")
    public ResponseEntity<AbstractResponse> deleteProductById(@PathVariable("restaurantId") String restaurantId,
                                                              @PathVariable("id") String id) {
        log.info("[START]: delete Product By Id");
        ProductResponse response = productService.deleteProductById(restaurantId, id);
        log.info("[END]: delete Product By Id");
        return ResponseEntity.ok(response);
    }
}
