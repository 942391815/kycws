package com.wxy.dg.common.service;

import com.wxy.dg.common.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */
public interface OrderService {

    public int save(Order order);

    public List<Order> findByCondition(Order order);

    public List<Order> findByCondition(Order order, int start, int limit);


    public Order findByPK(long pk);

    public void update(Order order);

    public void delete(Long pk);


    public Integer getTotalCount(Order order);

    public boolean purchaseOrder(String openId,String orderId);

    public List<Map<String,String>> findOrderByOpenId(String openId);
}
