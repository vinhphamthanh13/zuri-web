package com.ocha.boc.services.impl;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.repository.NguyenLieuRepository;
import com.ocha.boc.request.NguyenLieuRequest;
import com.ocha.boc.response.NguyenLieuResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NguyenLieuService {

    @Autowired
    private NguyenLieuRepository nguyenLieuRepository;

    public NguyenLieuResponse newNguyenLieu (NguyenLieuRequest request){
        return null;
    }

    public NguyenLieuResponse updateNguyenLieuInformation (NguyenLieuRequest request){
        return null;
    }

    public NguyenLieuResponse findNguyenLieuById (String id){
        return null;
    }

    public NguyenLieuResponse getAllNguyenLieu(){
        return null;
    }

    public AbstractResponse deleteNguyenLieuById(String id){
        return null;
    }
}
