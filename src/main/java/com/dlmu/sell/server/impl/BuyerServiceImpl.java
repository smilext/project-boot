package com.dlmu.sell.server.impl;

import com.dlmu.sell.enums.ResultEnum;
import com.dlmu.sell.exception.SellException;
import com.dlmu.sell.server.BuyerService;
import com.dlmu.sell.server.OrderService;
import com.dlmu.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BuyerServiceImpl implements BuyerService {
   @Autowired
   private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
       return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid,orderId);
        if(orderDTO == null){
            log.error("查不到该订单");
            throw new SellException(ResultEnum.ORDER_NOT_EXITTS);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner (String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO ==null){
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("订单的openid不一致");
            throw new SellException(ResultEnum.OWER_ERROR);
        }
        return orderDTO;
    }
}
