package com.ocha.boc.services.impl;

import com.ocha.boc.dto.ProductDTO;
import com.ocha.boc.entity.Product;
import com.ocha.boc.repository.ProductRepository;
import com.ocha.boc.request.ProductListRequest;
import com.ocha.boc.request.ProductRequest;
import com.ocha.boc.request.ProductUpdateRequest;
import com.ocha.boc.response.ProductResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createNewProduct(ProductRequest request) {
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.CREATE_NEW_PRODUCT_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (request != null) {
                if (StringUtils.isNotEmpty(request.getName())) {
                    if (!checkProductExisted(request.getName(), request.getRestaurantId())) {
                        Product product = new Product();
                        product.setRestaurantId(request.getRestaurantId());
                        product.setName(request.getName());
                        product.setCategoryId(request.getCategoryId());
                        if (CollectionUtils.isNotEmpty(request.getPrices())) {
                            product.setPrices(request.getPrices());
                        }
                        product.setCreatedDate(DateUtils.getCurrentDateAndTime());
                        response.setSuccess(Boolean.TRUE);
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new ProductDTO(product));
                        productRepository.save(product);
                    } else {
                        response.setMessage(CommonConstants.PRODUCT_IS_EXISTED);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when create new product: ", e);
        }
        return response;
    }

    //@CachePut(value = "mathang", key = "{#request.cuaHangId, #request.Id}")
    public Product updateProductInfor(ProductUpdateRequest request) {
        return productRepository.findProductByIdAndRestaurantId(request.getId(),
                request.getRestaurantId()).map(product -> {
            if (StringUtils.isNotEmpty(request.getName())) {
                product.setName(request.getName());
            }
            if (CollectionUtils.isNotEmpty(request.getPrices())) {
                product.setPrices(request.getPrices());
            }
            if (StringUtils.isNotEmpty(request.getCategoryId())) {
                product.setCategoryId(request.getCategoryId());
            }
            product.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
            return productRepository.save(product);
        }).orElse(new Product());
    }

    //@Cacheable(value = "mathang", key = "{#cuaHangId,#id}")
    public Product findProductById(String restaurantId, String id) {
        return productRepository.findProductByIdAndRestaurantId(id, restaurantId).orElse(new Product());
    }

    public ProductResponse getAllProduct(String restaurantId) {
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.GET_ALL_PRODUCT_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            List<Product> productList = productRepository.findAllByRestaurantId(restaurantId);
            if (CollectionUtils.isNotEmpty(productList)) {
                List<ProductDTO> productDTOList = new ArrayList<>();
                for (Product product : productList) {
                    ProductDTO temp = new ProductDTO(product);
                    productDTOList.add(temp);
                }
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                response.setTotalResultCount((long) productList.size());
                response.setObjects(productDTOList);
            }
        } catch (Exception e) {
            log.error("Error when get all products: ", e);
        }
        return response;
    }

    public Page<Product> search(ProductListRequest request) {
        String[] sortSplit = request.getSort().split(",");
        if (!Objects.isNull(request.getSearch())) {
            return productRepository.query(request, new org.springframework.data.domain.PageRequest(request.getPage(),
                    request.getSize(),
                    (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                            : Sort.Direction.DESC), sortSplit[0]));
        } else {
            return productRepository.findAll(
                    new org.springframework.data.domain.PageRequest(request.getPage(),
                            request.getSize(),
                            (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                    : Sort.Direction.DESC), sortSplit[0]));
        }
    }

    //@CacheEvict(value = "mathang", key = "{#cuaHangId,#id}")
    public ProductResponse deleteProductById(String restaurantId, String id) {
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.DELETE_PRODUCT_BY_PRODUCT_ID_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                if (productRepository.existsByIdAndRestaurantId(id, restaurantId)) {
                    Optional<Product> optMatHang = productRepository.findProductByIdAndRestaurantId(id, restaurantId);
                    productRepository.delete(optMatHang.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                } else {
                    response.setMessage(CommonConstants.PRODUCT_IS_NULL);
                }
            }
        } catch (Exception e) {
            log.error("Error when delete product by id: ", e);
        }
        return response;
    }

    private boolean checkProductExisted(String name, String restaurantId) {
        return productRepository.existsByNameAndRestaurantId(name, restaurantId);
    }

}
