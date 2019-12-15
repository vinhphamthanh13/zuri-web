package com.ocha.boc.controllers;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.request.ProductListRequest;
import com.ocha.boc.request.ProductRequest;
import com.ocha.boc.request.ProductUpdateRequest;
import com.ocha.boc.response.ProductResponse;
import com.ocha.boc.services.impl.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<ProductResponse> updateProduct(@Valid @RequestBody ProductUpdateRequest request) {
        log.info("[START]: update Product");
        ProductResponse response = productService.updateProduct(request);
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
        ProductResponse response = productService.findProductById(restaurantId, id);
        log.info("[END]: find products by Id");
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Product Full Text Search", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/products/search")
    public ResponseEntity<ProductResponse> search(ProductListRequest request) {
        log.info("[START]: get all products");
        ProductResponse response = productService.search(request);
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
