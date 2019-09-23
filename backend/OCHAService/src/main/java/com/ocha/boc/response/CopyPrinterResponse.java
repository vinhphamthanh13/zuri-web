package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.CopyPrinterDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CopyPrinterResponse extends AbstractResponse<String, CopyPrinterDTO> implements Serializable {
}
