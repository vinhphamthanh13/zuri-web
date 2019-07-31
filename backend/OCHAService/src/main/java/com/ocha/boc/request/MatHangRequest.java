package com.ocha.boc.request;

import com.ocha.boc.entity.BangGia;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatHangRequest implements Serializable {

    private String cuaHangId;

    private String name;

    private String danhMucId;

    private List<BangGia> listBangGia = new ArrayList<BangGia>();

    private String khuyenMaiId;


}
