package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.GiamGiaDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class GiamGiaResponse extends AbstractResponse<String, GiamGiaDTO> implements Serializable {
}
