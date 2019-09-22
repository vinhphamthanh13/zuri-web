package com.ocha.boc.dto;

import com.ocha.boc.base.AbstractEntity;
import com.ocha.boc.entity.ProductPortfolio;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductPortfolioDTO extends AbstractEntity {

    private String name;

    private String id;

    public ProductPortfolioDTO(ProductPortfolio productPortfolio) {
        this.id = productPortfolio.getId();
        this.name = productPortfolio.getName();
    }
}
