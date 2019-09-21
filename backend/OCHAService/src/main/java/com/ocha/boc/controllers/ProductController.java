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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<ProductResponse> createNewProduct(@RequestBody ProductRequest request) {
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
    public ResponseEntity<ProductResponse> updateProductInfor(@RequestBody ProductUpdateRequest request) {
        log.info("[START]: update Product");
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.UPDATE_PRODUCT_FAIL);
        response.setSuccess(Boolean.FALSE);
        Optional<Product> optProduct = Optional
                .ofNullable(productService.updateProductInfor(request));
        if (optProduct.isPresent()) {
            if (!optProduct.get().checkObjectEmptyData()) {
                response.setObject(new ProductDTO(optProduct.get()));
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            } else {
                response.setMessage(CommonConstants.PRODUCT_IS_NULL);
            }
        }
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
        Optional<Product> matHangOptional = Optional
                .ofNullable(productService.findProductById(restaurantId, id));
        if (matHangOptional.isPresent()) {
            if (!matHangOptional.get().checkObjectEmptyData()) {
                response.setObject(new ProductDTO(matHangOptional.get()));
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
            }
        }
        log.info("[END]: find products by Id");
        return ResponseEntity.ok(response);
    }

//    /**
//     * Get All Products
//     *
//     * @param cuaHangId
//     * @return
//     */
//    @ApiOperation(value = "Get All Mat Hang", authorizations = {@Authorization(value = "Bearer")})
//    @GetMapping("/mat-hang")
//    public ResponseEntity<ProductResponse> getAllMatHang(@RequestParam String cuaHangId) {
//        log.info("[START]: get all Mat Hang");
//        ProductResponse response = productService.getAllMatHang(cuaHangId);
//        log.info("[END]: get all Mat Hang");
//        return ResponseEntity.ok(response);
//    }

    @ApiOperation(value = "Get All Products", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/products/search")
    public ResponseEntity<ProductResponse> getAllProducts(ProductListRequest request) {
        ProductResponse response = new ProductResponse();
        Page<Product> temp = productService.search(request);
        List<Product> tempList = temp.getContent();
        List<ProductDTO> result = new ArrayList<ProductDTO>();
        for (Product product : tempList) {
            result.add(new ProductDTO(product));
        }
        response.setObjects(result);
        response.setSuccess(Boolean.TRUE);
        response.setTotalResultCount((long) result.size());
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
