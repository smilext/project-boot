package com.dlmu.sell.dao;

import com.dlmu.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
List<OrderDetail> findByOrderId(String orderId);

}
