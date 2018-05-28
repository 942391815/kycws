package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.dao.OrderDao;
import com.wxy.dg.common.model.ConsumerOrder;
import com.wxy.dg.common.model.ConsumerOrderRecord;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.service.ConsumerOrderRecordService;
import com.wxy.dg.common.service.ConsumerOrderService;
import com.wxy.dg.common.service.ConsumerService;
import com.wxy.dg.common.service.OrderService;
import com.wxy.dg.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private ConsumerOrderService consumerOrderService;
    @Autowired
    private ConsumerOrderRecordService consumerOrderRecordService;


    @Override
    public int save(Order order) {
        order.setYn(1);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        return  orderDao.save(order);
    }

    @Override
    public List<Order> findByCondition(Order order ) {
        return  orderDao.findByCondition(order);
    }

    @Override
    public List<Order> findByCondition(Order order , int start, int limit) {
        return orderDao.findByCondition(order,start,limit);
    }

    @Override
    public Order findByPK(long pk) {
        return orderDao.findByPK(pk,Order.class);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public void delete(Long pk) {
        Order order = new Order();
        order.setId(pk);
        order.setYn(0);
        order.setUpdateTime(new Date());
        orderDao.update(order);
    }

    @Override
    public Integer getTotalCount(Order order) {
        return orderDao.getTotalCount(order);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public boolean purchaseOrder(String openId, String orderId) {

        Order order = orderDao.findByPK(Long.parseLong(orderId), Order.class);
        if(order==null){
            return false;
        }
        int count = order.getCount();

        /**
         * 保存用户购买记录
         */
        ConsumerOrderRecord consumerOrderRecord = new ConsumerOrderRecord();
        consumerOrderRecord.setCount(count);
        consumerOrderRecord.setOpenId(openId);
        consumerOrderRecord.setOrderId(Integer.parseInt(orderId));
        consumerOrderRecord.setCreateTime(DateUtils.getDateTime());
        consumerOrderRecord.setUpdateTime(DateUtils.getDateTime());
        consumerOrderRecordService.save(consumerOrderRecord);

        /**
         * 查询用户是否之前购买过订单
         */
        ConsumerOrder consumerOrder = new ConsumerOrder();
        consumerOrder.setOpenId(openId);
        consumerOrder.setOrderId(Integer.parseInt(orderId));
        List<ConsumerOrder> consumerOrderList = consumerOrderService.findByCondition(consumerOrder);
        if(consumerOrderList!=null&&consumerOrderList.size()>0){
            consumerOrder = consumerOrderList.get(0);
            //之前购买过产品，此次购买为续费
            consumerOrder.setUpdateTime(DateUtils.getDateTime());
            consumerOrder.setCount(consumerOrder.getCount()+count);
            consumerOrderService.update(consumerOrder);
        }else{
            //之前没有购买过产品
            consumerOrder.setCreateTime(DateUtils.getDateTime());
            consumerOrder.setUpdateTime(DateUtils.getDateTime());
            consumerOrder.setCount(count);
            consumerOrderService.save(consumerOrder);
        }

        return true;
    }

    /**
     * 根据openId 查询用户的购买记录
     * @param openId
     * @return
     */
    @Override
    public List<Map<String, String>> findOrderByOpenId(String openId) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("openId",openId);
        return orderDao.findOrderByOpenId(map);
    }
}
