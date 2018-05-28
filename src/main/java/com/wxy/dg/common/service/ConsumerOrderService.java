package com.wxy.dg.common.service;

import com.wxy.dg.common.model.ConsumerOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */
public interface ConsumerOrderService {

        public int save(ConsumerOrder object);

        public List<ConsumerOrder> findByCondition(ConsumerOrder obj);

        public List<ConsumerOrder> findByCondition(ConsumerOrder obj, int start, int limit);


        public ConsumerOrder findByPK(long pk);

        public void update(ConsumerOrder user);

        public Integer getTotalCount(ConsumerOrder user);

    }
