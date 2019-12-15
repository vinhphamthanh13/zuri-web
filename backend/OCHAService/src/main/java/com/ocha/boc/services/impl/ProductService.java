package com.ocha.boc.services.impl;

import com.ocha.boc.dto.ProductDTO;
import com.ocha.boc.entity.Product;
import com.ocha.boc.enums.EntityType;
import com.ocha.boc.error.ResourceNotFoundException;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    if (checkProductExisted(request.getName(), request.getRestaurantId())) {
                        response.setMessage(CommonConstants.PRODUCT_IS_EXISTED);
                        return response;
                    }
                    Product product = Product.builder()
                            .restaurantId(request.getRestaurantId())
                            .name(request.getName())
                            .categoryId(request.getCategoryId())
                            .prices(request.getPrices())
                            .build();
                    product.setCreatedDate(DateUtils.getCurrentDateAndTime());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new ProductDTO(product));
                    productRepository.save(product);
                }
            }
        } catch (Exception e) {
            log.error("Error when create new product: ", e);
        }
        return response;
    }

    public ProductResponse updateProduct(ProductUpdateRequest request) {
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.UPDATE_PRODUCT_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            productRepository.findProductByIdAndRestaurantId(request.getId(),
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
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setSuccess(Boolean.TRUE);
                return productRepository.save(product);
            }).orElseThrow(() -> new ResourceNotFoundException(EntityType.PRODUCT.toString(), request.getId() +
                    " - " + request.getRestaurantId(), request));
        } catch (Exception e) {
            log.error("Exception while updating product: ", e);
        }
        return response;
    }

    public ProductResponse findProductById(String restaurantId, String id) {
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.PRODUCT_IS_NULL);
        response.setSuccess(Boolean.FALSE);
        try {
            Optional<Product> optProduct = productRepository.findProductByIdAndRestaurantId(id, restaurantId);
            if (optProduct.isPresent()) {
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                response.setObject(new ProductDTO(optProduct.get()));
            }
        } catch (Exception e) {
            log.error("Exception while finding product by id: ", e);
        }
        return response;
    }


    public ProductResponse search(ProductListRequest request) {
        ProductResponse response = new ProductResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.NO_RESULT_PRODUCT_SEARCHING);
        try {
            String[] sortSplit = request.getSort().split(",");
            Page<Product> result = null;
            if (!Objects.isNull(request.getSearch())) {
                result = productRepository.query(request, new org.springframework.data.domain.PageRequest(request.getPage(),
                        request.getSize(),
                        (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                : Sort.Direction.DESC), sortSplit[0]));
            } else {
                result = productRepository.findAll(
                        new org.springframework.data.domain.PageRequest(request.getPage(),
                                request.getSize(),
                                (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC
                                        : Sort.Direction.DESC), sortSplit[0]));
            }
            if (CollectionUtils.isNotEmpty(result.getContent())) {
                List<ProductDTO> lists = result.getContent().stream().map(ProductDTO::new).collect(Collectors.toList());
                response.setObjects(lists);
                response.setSuccess(Boolean.TRUE);
                response.setTotalResultCount((long) lists.size());
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
            }
        } catch (Exception e) {
            log.error("Exception while full text searching product: ", e);
        }
        return response;
    }

    public ProductResponse deleteProductById(String restaurantId, String id) {
        ProductResponse response = new ProductResponse();
        response.setMessage(CommonConstants.DELETE_PRODUCT_BY_PRODUCT_ID_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (StringUtils.isNotEmpty(id)) {
                if (!productRepository.existsByIdAndRestaurantId(id, restaurantId)) {
                    response.setMessage(CommonConstants.PRODUCT_IS_NULL);
                    return response;
                }
                Optional<Product> optProduct = productRepository.findProductByIdAndRestaurantId(id, restaurantId);
                productRepository.delete(optProduct.get());
                response.setSuccess(Boolean.TRUE);
                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
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
