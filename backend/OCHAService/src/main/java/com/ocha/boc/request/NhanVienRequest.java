package com.ocha.boc.request;

import com.ocha.boc.enums.NhanVienType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class NhanVienRequest implements Serializable {

    @NonNull
    private String cuaHangId;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @NonNull
    private String fullName;

    @NonNull
    private NhanVienType nhanVienType;
}
