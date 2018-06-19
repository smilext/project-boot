package com.dlmu.sell.dao;

import com.dlmu.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, org.springframework.data.domain.Pageable pageable);

}
