package com.ocha.boc.request;

import com.ocha.boc.entity.BangGia;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatHangUpdateRequest implements Serializable {

    @NonNull
    private String id;

    @NonNull
    private String cuaHangId;

    @NonNull
    private String name;

    private String danhMucId;

    private List<BangGia> listBangGia = new ArrayList<BangGia>();

    private String khuyenMaiId;

}
