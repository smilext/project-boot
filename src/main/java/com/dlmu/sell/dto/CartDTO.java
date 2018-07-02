package com.dlmu.sell.dto;

import lombok.Getter;

@Getter
public class CartDTO {
    //商品ID
    private  String productId;
    //商品数量
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
