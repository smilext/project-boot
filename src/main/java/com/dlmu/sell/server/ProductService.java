package com.dlmu.sell.server;

import com.dlmu.sell.dataobject.ProductInfo;
import com.dlmu.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    /*查询在架的商品列表*/
    List<ProductInfo> findUpAll();

    /* 查询所有
     * pageable分页需要的参数
     */
     Page<ProductInfo> findAll(Pageable pageable);

     ProductInfo save(ProductInfo productInfo);
    //加库存
    void incresaStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
}
