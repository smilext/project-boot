package com.dlmu.sell.server.impl;


import com.dlmu.sell.dao.ProductInfoRepository;
import com.dlmu.sell.dataobject.ProductInfo;
import com.dlmu.sell.enums.ProductStatusEnum;
import com.dlmu.sell.enums.ResultEnum;
import com.dlmu.sell.exception.SellException;
import com.dlmu.sell.server.ProductService;
import dto.CartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;
    @Override
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    public void incresaStock(List<CartDTO> cartDTOList) {

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
    for (CartDTO cartDTO:cartDTOList){
        ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXITS);
        }
       Integer result = productInfo.getProductStock()-cartDTO.getProductQuantity();
        if (result<0){
            throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
        }
        productInfo.setProductStock(result);
        repository.save(productInfo);

    }
    }
}
