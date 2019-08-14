package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.GiayIn;
import com.ocha.boc.entity.SystemConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SystemConfigDTO extends AbstractEntity {

    public List<GiayIn> listGiayInConfiguration;

    public SystemConfigDTO(SystemConfig systemConfig) {
        this.listGiayInConfiguration = systemConfig.getListGiayInConfiguration();
    }
}
