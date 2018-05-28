package com.wxy.dg.common.dao;

import com.wxy.dg.common.model.SmsCode;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.modules.dao.MyBatisBaseDao;

/**
 * Created by test on 2016/12/2.
 */
public interface SmsCodeDao extends MyBatisBaseDao<SmsCode,Long>{
    int getSmsCountByPhone(String smsNumber);
}
