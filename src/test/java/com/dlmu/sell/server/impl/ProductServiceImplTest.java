package com.dlmu.sell.server.impl;

import com.dlmu.sell.dataobject.ProductInfo;
import com.dlmu.sell.enums.ProductStatusEnum;
import com.dlmu.sell.server.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

   @Autowired
   private  ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("1234");
        Assert.assertEquals("1234",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> list =  productService.findUpAll();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productService.findAll(request);
      //   System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo(
                "12345",
                "绿豆汤",new BigDecimal(2.0),
                103,
                "有营养的粥",
                "http://xxxss.jpg",
                ProductStatusEnum.DOWN.getCode(),3);
        System.out.println("...................xiaomi");
                productService.save(productInfo);

    }
}