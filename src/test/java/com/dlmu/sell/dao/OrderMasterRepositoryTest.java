package com.dlmu.sell.dao;

import com.dlmu.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("13");
        orderMaster.setBuyerName("lulu");
        orderMaster.setBuyerPhone("11111");
        orderMaster.setBuyerAddress("XXXXXX");
        orderMaster.setBuyerOpenid("110101");
        orderMaster.setOrderAmount(new BigDecimal(2));
        orderMasterRepository.save(orderMaster);
    }
    @Test
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0,1);
        Page<OrderMaster> result= orderMasterRepository.findByBuyerOpenid("110101",request);
        System.out.println(result.getTotalElements());


    }
    @Test
    public void findOneTest(){

        OrderMaster orderMaster = orderMasterRepository.findOne("12");
        System.out.println(orderMaster.toString());

    }
}