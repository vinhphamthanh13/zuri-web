package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
public class JwtAuthenticationResponse extends AbstractResponse<String, UserDTO> implements Serializable {
    private String accessToken;

}
