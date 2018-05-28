package com.wxy.dg.common.service;

import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.model.SmsCode;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */
public interface SmsCodeService {

    public String sendCode(String mobile);

    public List<SmsCode> findByCondition(SmsCode smsCode);

    public List<SmsCode> findByCondition(SmsCode smsCode, int start, int limit);

    public String sendBackInfoDetail(Map<String,String> param);

    public int getSmsCountByPhone(String smsNumber);

    }
