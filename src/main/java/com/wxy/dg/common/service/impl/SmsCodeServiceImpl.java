package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.dao.SmsCodeDao;
import com.wxy.dg.common.model.SmsCode;
import com.wxy.dg.common.service.SmsCodeService;
import com.wxy.dg.common.util.sms.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/7.
 *
 * // TODO: 2016/12/18  我的任务--处理，要有处理的历史记录
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {
    @Autowired
    private SmsCodeDao smsCodeDao;

    @Override
    public String sendCode(String mobile) {
        SmsCode sendCode = new SmsCode();
        sendCode.setCreateTime(new Date());
        sendCode.setMobile(mobile);
        java.util.Random random=new java.util.Random();
        sendCode.setCode(random.nextInt(9999)+"");
        smsCodeDao.save(sendCode);
        SmsUtil.templateSMS(mobile,sendCode.getCode()+"",SmsUtil.TEMPLATEID);
        return sendCode.getCode();
    }

    @Override
    public List<SmsCode> findByCondition(SmsCode smsCode) {
        return smsCodeDao.findByCondition(smsCode);
    }

    @Override
    public List<SmsCode> findByCondition(SmsCode smsCode, int start, int limit) {
        return smsCodeDao.findByCondition(smsCode,start,limit );
    }

    @Override
    public String sendBackInfoDetail(Map<String, String> param) {
        String mobile = param.get("mob");
        String stitle = param.get("stitle");
        String url = param.get("url");
        String name = param.get("name");

        SmsCode sendCode = new SmsCode();
        sendCode.setCreateTime(new Date());
        sendCode.setMobile(mobile);
        sendCode.setCode("{name:\""+name+"\",title:\""+url+"\"}");
        smsCodeDao.save(sendCode);
        SmsUtil.templateSMS(mobile,name+","+url,SmsUtil.TEMPLATEIDDETAIL);
        return null;
    }

    @Override
    public int getSmsCountByPhone(String smsNumber) {
        return smsCodeDao.getSmsCountByPhone(smsNumber);
    }
}
