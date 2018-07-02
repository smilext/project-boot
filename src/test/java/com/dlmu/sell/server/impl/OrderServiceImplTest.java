package com.dlmu.sell.server.impl;

import com.dlmu.sell.converter.OrderFrom2OrderDTOConverter;
import com.dlmu.sell.dao.OrderDetailRepository;
import com.dlmu.sell.dao.OrderMasterRepository;
import com.dlmu.sell.dataobject.OrderDetail;
import com.dlmu.sell.enums.OrderStatusEnum;
import com.dlmu.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import com.dlmu.sell.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private final String BUYER_OPENID = "1101110";
    private final String ORDER_ID="1529391481927225392";
    @Test
    public void create() {
       OrderDTO  orderDTO = new OrderDTO();
        orderDTO.setBuyerName("wxt");
        orderDTO.setBuyerAddress("DLMU");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setBuyerPhone("123456456");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1234");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("12345");
        o2.setProductQuantity(1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("[创建订单] result={}", result);
        Assert.assertNotNull(result);

    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("[查询单个订单] result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());

    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());

    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());

    }

    @Test
    public void list(){
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

}