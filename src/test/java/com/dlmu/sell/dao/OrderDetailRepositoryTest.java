package com.dlmu.sell.dao;

import com.dlmu.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private  OrderDetailRepository repository ;
    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
         orderDetail.setDetailId("11");
         orderDetail.setOrderId("12");
         orderDetail.setProductId("1234");
         orderDetail.setProductName("鸡蛋");
         orderDetail.setProductPrice(new BigDecimal(2));
         orderDetail.setProductQuantity(2);
         orderDetail.setProductIcon("XXXXXX");
         repository.save(orderDetail);
    }
    @Test
    public void findByOrderIdTest(){
        List<OrderDetail> list = repository.findByOrderId("12");
        Assert.assertNotEquals(0,list.size());
    }
}