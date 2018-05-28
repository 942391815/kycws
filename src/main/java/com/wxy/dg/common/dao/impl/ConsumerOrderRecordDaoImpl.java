package com.wxy.dg.common.dao.impl;

import com.wxy.dg.common.dao.ConsumerOrderDao;
import com.wxy.dg.common.dao.ConsumerOrderRecordDao;
import com.wxy.dg.common.model.ConsumerOrder;
import com.wxy.dg.common.model.ConsumerOrderRecord;
import com.wxy.dg.modules.dao.MyBatisBaseDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Created by test on 2016/12/3.
 */

@Component
public class ConsumerOrderRecordDaoImpl extends MyBatisBaseDaoImpl<ConsumerOrderRecord, Long> implements ConsumerOrderRecordDao {

}
