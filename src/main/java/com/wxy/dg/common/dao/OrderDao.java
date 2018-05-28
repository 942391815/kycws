package com.wxy.dg.common.dao;

import com.wxy.dg.common.model.Order;
import com.wxy.dg.modules.dao.MyBatisBaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */
public interface OrderDao extends MyBatisBaseDao<Order, Long> {

    public List<Map<String, String>> findOrderByOpenId(Map<String, String> order);

}
