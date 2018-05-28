package com.wxy.dg.common.dao.impl;

import com.wxy.dg.common.dao.BackInfoDao;
import com.wxy.dg.common.dao.OrderDao;
import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.modules.dao.MyBatisBaseDaoImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */

@Component
public class OrderDaoImpl extends MyBatisBaseDaoImpl<Order, Long> implements OrderDao {

    @Override
    public List<Map<String, String>> findOrderByOpenId(Map<String,String> order) {
        return this.getSqlSessionTemplate().selectList("com.wxy.dg.common.model.Order.getOrderByOpenId",order);
    }
}
