package com.wxy.dg.common.service;

import com.wxy.dg.common.model.ConsumerOrder;
import com.wxy.dg.common.model.ConsumerOrderRecord;

import java.util.List;

/**
 * Created by test on 2016/12/2.
 */
public interface ConsumerOrderRecordService {

        public int save(ConsumerOrderRecord object);

        public List<ConsumerOrderRecord> findByCondition(ConsumerOrderRecord obj);

        public List<ConsumerOrderRecord> findByCondition(ConsumerOrderRecord obj, int start, int limit);


        public ConsumerOrderRecord findByPK(long pk);

        public void update(ConsumerOrderRecord user);

        public Integer getTotalCount(ConsumerOrderRecord user);

    }
