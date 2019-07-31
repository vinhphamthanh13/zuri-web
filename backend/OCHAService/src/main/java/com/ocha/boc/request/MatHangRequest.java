package com.ocha.boc.request;

import com.ocha.boc.entity.BangGia;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatHangRequest implements Serializable {

    @NonNull
    private String cuaHangId;

    @NonNull
    private String name;

    @NonNull
    private String danhMucId;

    @NonNull
    private List<BangGia> listBangGia = new ArrayList<BangGia>();

    private String khuyenMaiId;


}
