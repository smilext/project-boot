package dto;

import com.dlmu.sell.dataobject.OrderDetail;
import com.dlmu.sell.enums.OrderStatusEnum;
import com.dlmu.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private  String orderId;

    private  String buyerName;

    private  String buyerPhone;
    private  String buyerAddress;
    //买家微信openid
    private  String buyerOpenid;
    //订单总金额
    private BigDecimal orderAmount;
    //订单状态, 默认0为新下单
    private Integer orderStatus;
    //支付状态, 默认0未支付
    private  Integer payStatus;

    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    List<OrderDetail> orderDetailList;
}
