package com.dlmu.sell.server.impl;

import com.dlmu.sell.converter.OrderMaster2orderDTOConverter;
import com.dlmu.sell.dao.OrderDetailRepository;
import com.dlmu.sell.dao.OrderMasterRepository;
import com.dlmu.sell.dataobject.OrderDetail;
import com.dlmu.sell.dataobject.OrderMaster;
import com.dlmu.sell.dataobject.ProductInfo;
import com.dlmu.sell.enums.OrderStatusEnum;
import com.dlmu.sell.enums.PayStatusEnum;
import com.dlmu.sell.enums.ResultEnum;
import com.dlmu.sell.exception.SellException;
import com.dlmu.sell.server.OrderService;
import com.dlmu.sell.server.ProductService;
import com.dlmu.sell.utils.KeyUtil;
import dto.CartDTO;
import dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.rmi.CORBA.Util;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        List<CartDTO> cartDTOList= new ArrayList<>();
        //1.查询商品（数量 价格）
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
          ProductInfo productInfo =productService.findOne(orderDetail.getProductId());
          if (productInfo == null){
             throw new SellException(ResultEnum.PRODUCT_NOT_EXITS);
          }
          //2.计算总价
         orderAmount = productInfo.getProductPrice()
                 .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                 .add(orderAmount);
          //订单入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);


        }
        //
        //3.写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXITTS);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
       Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
       List<OrderDTO> orderDTOList = OrderMaster2orderDTOConverter.convert(  orderMasterPage.getContent());

       Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确 ，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
       OrderMaster updateResult =  orderMasterRepository.save(orderMaster);
       if (updateResult == null){
           log.error("【取消订单】更新失败，orderMaster={}",orderMaster);
           throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
       }
        //  返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
           log.error("【取消订单】订单中无商品详情 ，orderDTO={}", orderDTO);
           throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
       }
       List<CartDTO> cartDTOList =  new ArrayList<>();
       for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
           CartDTO cartDTO = new CartDTO( orderDetail.getProductId(),orderDetail.getProductQuantity());
           cartDTOList.add(cartDTO);
       }
       productService.incresaStock(cartDTOList);
        //如果已经支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        // 修改订单状态
        return null;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        // 判断支付状态
        // 修改支付状态
        return null;
    }
}