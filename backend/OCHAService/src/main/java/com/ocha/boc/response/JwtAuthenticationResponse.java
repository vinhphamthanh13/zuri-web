package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAuthenticationResponse extends AbstractResponse implements Serializable {
    private String accessToken;

}
