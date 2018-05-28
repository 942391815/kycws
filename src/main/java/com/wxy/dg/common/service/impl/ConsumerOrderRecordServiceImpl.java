package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.dao.ConsumerOrderDao;
import com.wxy.dg.common.dao.ConsumerOrderRecordDao;
import com.wxy.dg.common.model.ConsumerOrder;
import com.wxy.dg.common.model.ConsumerOrderRecord;
import com.wxy.dg.common.service.ConsumerOrderRecordService;
import com.wxy.dg.common.service.ConsumerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by test on 2016/12/2.
 */

@Service
public class ConsumerOrderRecordServiceImpl implements ConsumerOrderRecordService {
    @Autowired
    private ConsumerOrderRecordDao consumerOrderRecordDao;

    @Override
    public int save(ConsumerOrderRecord order) {
        return  consumerOrderRecordDao.save(order);
    }

    @Override
    public List<ConsumerOrderRecord> findByCondition(ConsumerOrderRecord order ) {
        return  consumerOrderRecordDao.findByCondition(order);
    }

    @Override
    public List<ConsumerOrderRecord> findByCondition(ConsumerOrderRecord consumerOrder , int start, int limit) {
        return consumerOrderRecordDao.findByCondition(consumerOrder,start,limit);
    }

    @Override
    public ConsumerOrderRecord findByPK(long pk) {
        return consumerOrderRecordDao.findByPK(pk,ConsumerOrderRecord.class);
    }

    @Override
    public void update(ConsumerOrderRecord consumerOrder) {
        consumerOrderRecordDao.update(consumerOrder);
    }

    @Override
    public Integer getTotalCount(ConsumerOrderRecord consumerOrder) {
        return consumerOrderRecordDao.getTotalCount(consumerOrder);
    }
}
