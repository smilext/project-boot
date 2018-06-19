package com.dlmu.sell.dataobject;

import com.dlmu.sell.enums.OrderStatusEnum;
import com.dlmu.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    private  String orderId;

    private  String buyerName;

    private  String buyerPhone;
    private  String buyerAddress;
    //买家微信openid
    private  String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    //订单状态, 默认0为新下单
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    //支付状态, 默认0未支付
   private  Integer payStatus = PayStatusEnum.WAIT.getCode();

   //创建时间
    private Date createTime;
   //更新时间
    private Date updateTime;

}
