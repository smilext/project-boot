package com.dlmu.sell.converter;

import com.dlmu.sell.dataobject.OrderDetail;
import com.dlmu.sell.enums.ResultEnum;
import com.dlmu.sell.exception.SellException;
import com.dlmu.sell.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.dlmu.sell.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderFrom2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderFrom) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderFrom.getName());
        orderDTO.setBuyerPhone(orderFrom.getPhone());
        orderDTO.setBuyerAddress(orderFrom.getAddress());
        orderDTO.setBuyerOpenid(orderFrom.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderFrom.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e){
            log.error("【对象转换】错误，string={}",orderFrom.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;

    }
}
