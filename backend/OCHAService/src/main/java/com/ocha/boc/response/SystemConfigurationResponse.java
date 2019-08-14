package com.ocha.boc.response;

import com.ocha.boc.base.AbstractResponse;
import com.ocha.boc.dto.SystemConfigDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SystemConfigurationResponse extends AbstractResponse<String , SystemConfigDTO> implements Serializable {
}
