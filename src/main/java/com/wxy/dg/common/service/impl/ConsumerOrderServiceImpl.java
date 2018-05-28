package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.dao.ConsumerOrderDao;
import com.wxy.dg.common.dao.OrderDao;
import com.wxy.dg.common.model.ConsumerOrder;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.service.ConsumerOrderService;
import com.wxy.dg.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by test on 2016/12/2.
 */

@Service
public class ConsumerOrderServiceImpl implements ConsumerOrderService {
    @Autowired
    private ConsumerOrderDao consumerOrderDao;

    @Override
    public int save(ConsumerOrder order) {
        return  consumerOrderDao.save(order);
    }

    @Override
    public List<ConsumerOrder> findByCondition(ConsumerOrder order ) {
        return  consumerOrderDao.findByCondition(order);
    }

    @Override
    public List<ConsumerOrder> findByCondition(ConsumerOrder consumerOrder , int start, int limit) {
        return consumerOrderDao.findByCondition(consumerOrder,start,limit);
    }

    @Override
    public ConsumerOrder findByPK(long pk) {
        return consumerOrderDao.findByPK(pk,ConsumerOrder.class);
    }

    @Override
    public void update(ConsumerOrder consumerOrder) {
        consumerOrderDao.update(consumerOrder);
    }

    @Override
    public Integer getTotalCount(ConsumerOrder consumerOrder) {
        return consumerOrderDao.getTotalCount(consumerOrder);
    }
}
