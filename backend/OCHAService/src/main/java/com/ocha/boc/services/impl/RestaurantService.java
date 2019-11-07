package com.ocha.boc.services.impl;

import com.ocha.boc.dto.RestaurantDTO;
import com.ocha.boc.entity.Restaurant;
import com.ocha.boc.entity.User;
import com.ocha.boc.enums.EProductPortfolioType;
import com.ocha.boc.repository.RestaurantRepository;
import com.ocha.boc.repository.UserRepository;
import com.ocha.boc.request.RestaurantRequest;
import com.ocha.boc.request.RestaurantUpdateRequest;
import com.ocha.boc.response.RestaurantResponse;
import com.ocha.boc.util.CommonConstants;
import com.ocha.boc.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    public RestaurantResponse createRestaurants(RestaurantRequest request) {
        RestaurantResponse response = new RestaurantResponse();
        response.setMessage(CommonConstants.CREATE_NEW_RESTAURANT_FAIL);
        response.setSuccess(Boolean.FALSE);
        try {
            if (!Objects.isNull(request)) {
                if (!checkInforCuaHangIsExisted(request.getAddress(), request.getRestaurantName())) {
                    if (!userRepository.existsByPhone(request.getPhone())) {
                        log.debug("Cannot find user by owner phone: ", request.getPhone());
                        response.setMessage(CommonConstants.UPDATE_RESTAURANT_ID_ON_USER_FAIL);
                        return response;
                    }
                    Restaurant restaurant = Restaurant.builder()
                            .restaurantName(request.getRestaurantName())
                            .phone(request.getPhone())
                            .managerName(request.getManagerName())
                            .businessItemsType(request.getBusinessItemsType())
                            .businessModelsType(request.getBusinessModelsType())
                            .address(request.getAddress())
                            .createdDate(DateUtils.getCurrentDateAndTime())
                            .managerPhone(request.getManagerPhone())
                            .build();
                    if (StringUtils.isNotEmpty(request.getManagerEmail())) {
                        restaurant.setManagerEmail(request.getManagerEmail());
                    }
                    Restaurant newRestaurant = restaurantRepository.save(restaurant);
                    //Update cua hang id on table user
                    Optional<User> optOwner = userRepository.findUserByPhone(request.getPhone());
                    List<Restaurant> ownerRestaurantList = optOwner.get().getListRestaurant();
                    ownerRestaurantList.add(newRestaurant);
                    optOwner.get().setListRestaurant(ownerRestaurantList);
                    userRepository.save(optOwner.get());
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new RestaurantDTO(newRestaurant));
                }
            }
        } catch (Exception e) {
            log.error("Error while createCuaHang: {}", e);
        }
        return response;
    }

    public RestaurantResponse updateEmailRestaurants(RestaurantUpdateRequest request) {
        RestaurantResponse response = new RestaurantResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.UPDATE_RESTAURANT_FAIL);
        try {
            if (!Objects.isNull(request)) {
                if (userRepository.existsByPhone(request.getPhone())) {
                    if (restaurantRepository.existsById(request.getRestaurantId())) {
                        Optional<Restaurant> optRestaurant = restaurantRepository.findRestaurantById(request.getRestaurantId()).map(restaurant -> {
                            if (StringUtils.isNotEmpty(request.getAddress())) {
                                restaurant.setAddress(request.getAddress());
                            }
                            if (StringUtils.isNotEmpty(request.getManagerEmail())) {
                                restaurant.setManagerEmail(request.getManagerEmail());
                            }
                            if (StringUtils.isNotEmpty(request.getBusinessModelsType())) {
                                restaurant.setBusinessModelsType(request.getBusinessModelsType());
                            }
                            if (StringUtils.isNotEmpty(request.getBusinessItemsType())) {
                                restaurant.setBusinessItemsType(EProductPortfolioType.valueOf(request.getBusinessItemsType()).label);
                            }
                            if (StringUtils.isNotEmpty(request.getManagerPhone())) {
                                restaurant.setManagerPhone(request.getManagerPhone());
                            }
                            if (StringUtils.isNotEmpty(request.getManagerName())) {
                                restaurant.setManagerName(request.getManagerName());
                            }
                            restaurant.setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                            return restaurantRepository.save(restaurant);
                        });
                        Optional<User> optionalUser = userRepository.findUserByPhone(request.getPhone());
                        List<Restaurant> lists = optionalUser.get().getListRestaurant().stream().map(restaurant -> {
                            if (restaurant.getId().equalsIgnoreCase(request.getRestaurantId())) {
                                restaurant = optRestaurant.get();
                            }
                            return restaurant;
                        }).collect(Collectors.toList());
                        optionalUser.get().setListRestaurant(lists);
                        userRepository.save(optionalUser.get());
                        response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                        response.setObject(new RestaurantDTO(optRestaurant.get()));
                        response.setSuccess(Boolean.TRUE);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while updateEmailCuaHang: {}", e);
        }
        return response;
    }


    private boolean checkInforCuaHangIsExisted(String location, String restaurantName) {
        return restaurantRepository.existsByAddressAndRestaurantName(location, restaurantName);
    }

    public RestaurantResponse findCuaHangByRestaurantId(String cuaHangId) {
        RestaurantResponse response = new RestaurantResponse();
        response.setSuccess(Boolean.FALSE);
        response.setMessage(CommonConstants.RESTAURANT_IS_NOT_EXISTED);
        try {
            if (StringUtils.isNotEmpty(cuaHangId)) {
                if (restaurantRepository.existsById(cuaHangId)) {
                    Optional<Restaurant> optRestaurant = restaurantRepository.findRestaurantById(cuaHangId);
                    response.setSuccess(Boolean.TRUE);
                    response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                    response.setObject(new RestaurantDTO(optRestaurant.get()));
                }
            }
        } catch (Exception e) {
            log.error("Error when findCuaHangByCuaHangId: {}", e);
        }
        return response;
    }
}
