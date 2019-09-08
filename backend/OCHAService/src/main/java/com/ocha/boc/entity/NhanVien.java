package com.ocha.boc.entity;

import com.ocha.boc.enums.NhanVienType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = NhanVien.COLLECTION_NAME)
public class NhanVien {

    public static final String COLLECTION_NAME = "nhanvien";

    @Id
    private String id;

    private String createdDate;

    private String lastModifiedDate;

    private String cuaHangId;

    private String username;

    private String password;

    private String fullName;

    private NhanVienType nhanVienType;

}
