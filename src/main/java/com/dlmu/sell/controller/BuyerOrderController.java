package com.dlmu.sell.controller;

import com.dlmu.sell.Vo.ResultVo;
import com.dlmu.sell.converter.OrderFrom2OrderDTOConverter;
import com.dlmu.sell.enums.ResultEnum;
import com.dlmu.sell.exception.SellException;
import com.dlmu.sell.form.OrderForm;
import com.dlmu.sell.server.OrderService;
import com.dlmu.sell.utils.ResultVoUtil;
import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    //创建订单
    @RequestMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
       if (bindingResult.hasErrors()){
          log.error("【创建订单】 参数不正确，orderForm={}",orderForm);
          throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                  bindingResult.getFieldError().getDefaultMessage());
       }
        OrderDTO orderDTO = OrderFrom2OrderDTOConverter.convert(orderForm);
       if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
        log.error("购物车不能为空");
        throw new SellException(ResultEnum.CART_EMPTY);
       }
       OrderDTO createResult = orderService.create(orderDTO);

       Map<String,String> map = new HashMap<>();
       map.put("orderId",createResult.getOrderId());

       return ResultVoUtil.sucess(map);
    }
    //订单列表
    @RequestMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                          @RequestParam(value = "page",defaultValue = "0") Integer page,
                                          @RequestParam(value = "size",defaultValue = "10") Integer size
                                         ){
        if(StringUtils.isEmpty(openid)){
            log.error("openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);
        return  ResultVoUtil.sucess(orderDTOPage.getContent());

    }

    //订单详情
    @RequestMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("openid") String openid ,
                                     @RequestParam("orderId") String orderId){
// TODO 不安全做法 改进
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){

        }
        return ResultVoUtil.sucess(orderDTO);
    }
    // 取消订单
    @RequestMapping("/cancel")
    public ResultVo<OrderDTO> cancel(@RequestParam("openid") String openid ,
                                     @RequestParam("orderId") String orderId){
// TODO 不安全做法 改进
        OrderDTO orderDTO = orderService.findOne(orderId);
        orderService.cancel(orderDTO);
        return ResultVoUtil.sucess();
    }


}
