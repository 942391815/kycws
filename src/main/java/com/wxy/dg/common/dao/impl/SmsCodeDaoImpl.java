package com.wxy.dg.common.dao.impl;

import com.wxy.dg.common.dao.OrderDao;
import com.wxy.dg.common.dao.SmsCodeDao;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.model.SmsCode;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.modules.dao.MyBatisBaseDaoImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */

@Component
public class SmsCodeDaoImpl extends MyBatisBaseDaoImpl<SmsCode, Long> implements SmsCodeDao {

    @Override
    public int getSmsCountByPhone(String smsNumber) {
        Map<String,String> param = new HashMap<String,String>();
        param.put("phone",smsNumber);
        param.put("date", DateUtils.getDate());
        return getSqlSessionTemplate().selectOne("getSmsCountByPhone",param);
    }
}
