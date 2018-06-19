package com.dlmu.sell.Vo;

import com.dlmu.sell.dataobject.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品 包含类目
 * @JsonPropert 使cateName与前段返回的name保持一致
 */
@Data
public class ProductVo {

    @JsonProperty("name")
    private  String categoryName;
    @JsonProperty("type")
    private  Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}
