package com.ocha.boc.services.impl;

import com.ocha.boc.dto.RestaurantDTO;
import com.ocha.boc.entity.Restaurant;
import com.ocha.boc.entity.User;
import com.ocha.boc.enums.EBusinessModelsType;
import com.ocha.boc.enums.EProductPortfolioType;
import com.ocha.boc.enums.UserType;
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
                if (StringUtils.isNotEmpty(request.getAddress()) && StringUtils.isNotEmpty(request.getRestaurantName())) {
                    if (!checkInforCuaHangIsExisted(request.getAddress(), request.getRestaurantName())) {
                        Optional<User> optOwner = userRepository.findUserByPhone(request.getPhone());
                        if (optOwner.isPresent()) {
                            Restaurant restaurant = new Restaurant();
                            restaurant.setRestaurantName(request.getRestaurantName());
                            restaurant.setPhone(request.getPhone());
                            restaurant.setManagerName(request.getManagerName());
//                            restaurant.setBusinessModelsType(EBusinessModelsType.valueOf(request.getBusinessModelsType()).label);
//                            restaurant.setBusinessItemsType(EProductPortfolioType.valueOf(request.getBusinessItemsType()).label);
                            restaurant.setBusinessItemsType(request.getBusinessModelsType());
                            restaurant.setBusinessItemsType(request.getBusinessItemsType());
                            restaurant.setManagerPhone(request.getManagerPhone());
                            restaurant.setCreatedDate(DateUtils.getCurrentDateAndTime());
                            restaurant.setAddress(request.getAddress());
                            if (StringUtils.isNotEmpty(request.getManagerEmail())) {
                                restaurant.setManagerEmail(request.getManagerEmail());
                            }
                            restaurantRepository.save(restaurant);
                            //Update cua hang id on table user
                            restaurant = restaurantRepository.findTopByOrderByCreatedDateDesc();
                            List<Restaurant> listRestaurant = optOwner.get().getListRestaurant();
                            listRestaurant.add(restaurant);
                            optOwner.get().setListRestaurant(listRestaurant);
                            optOwner.get().setRole(UserType.ADMIN);
                            userRepository.save(optOwner.get());
                            //Check manager Phone exist in the system. If existed then assign cuaHangId to the account, if not
                            //Create new account with phone of the manager.
                            /*
                            if(!request.getManagerPhone().equalsIgnoreCase(request.getPhone())) {
                                Optional<User> optManager = userRepository.findUserByPhone(request.getManagerPhone());
                                if (!optManager.isPresent()) {
                                    User manager = new User();
                                    manager.setPhone(request.getManagerPhone());
                                    manager.setEmail(request.getManagerEmail());
                                    manager.setName(request.getManagerName());
                                    manager.setActive(Boolean.TRUE);
                                    manager.setCreatedDate(Instant.now().toString());
                                    manager.setRole(UserType.USER);
                                    manager.setListRestaurant(new ArrayList<Restaurant>());
                                    manager.getListRestaurant().add(restaurant);

                                }else{
                                    List<Restaurant> list = optManager.get().getListRestaurant();
                                    list.add(restaurant);
                                    optManager.get().setListRestaurant(list);
                                }
                                userRepository.save(optManager.get());
                                response.setSuccess(Boolean.TRUE);
                                response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                                response.setObject(new RestaurantDTO(restaurant));
                            }
                            */
                            response.setSuccess(Boolean.TRUE);
                            response.setMessage(CommonConstants.STR_SUCCESS_STATUS);
                            response.setObject(new RestaurantDTO(restaurant));
                        } else {
                            log.debug("Cannot find user by owner phone: ", request.getPhone());
                            response.setMessage(CommonConstants.UPDATE_RESTAURANT_ID_ON_USER_FAIL);
                        }
                    }
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
                    Optional<User> optionalUser = userRepository.findUserByPhone(request.getPhone());
                    if (StringUtils.isNotEmpty(request.getRestaurantId())) {
                        if (restaurantRepository.existsById(request.getRestaurantId())) {
                            Optional<Restaurant> optRestaurant = restaurantRepository.findRestaurantById(request.getRestaurantId());
                            if (StringUtils.isNotEmpty(request.getAddress())) {
                                optRestaurant.get().setAddress(request.getAddress());
                            }
                            if (StringUtils.isNotEmpty(request.getManagerEmail())) {
                                optRestaurant.get().setManagerEmail(request.getManagerEmail());
                            }
                            if (StringUtils.isNotEmpty(request.getBusinessModelsType())) {
                                optRestaurant.get().setBusinessModelsType(EBusinessModelsType.valueOf(request.getBusinessModelsType()).label);
                            }
                            if(StringUtils.isNotEmpty(request.getBusinessItemsType())){
                                optRestaurant.get().setBusinessItemsType(EProductPortfolioType.valueOf(request.getBusinessItemsType()).label);
                            }
                            if (StringUtils.isNotEmpty(request.getManagerPhone())) {
                                optRestaurant.get().setManagerPhone(request.getManagerPhone());
                            }
                            if (StringUtils.isNotEmpty(request.getManagerName())) {
                                optRestaurant.get().setManagerName(request.getManagerName());
                            }
                            optRestaurant.get().setLastModifiedDate(DateUtils.getCurrentDateAndTime());
                            restaurantRepository.save(optRestaurant.get());
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
