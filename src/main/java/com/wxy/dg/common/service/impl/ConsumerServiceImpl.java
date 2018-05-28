package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.dao.ConsumerDao;
import com.wxy.dg.common.dao.SmsCodeDao;
import com.wxy.dg.common.model.Consumer;
import com.wxy.dg.common.model.SmsCode;
import com.wxy.dg.common.service.ConsumerService;
import com.wxy.dg.common.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private ConsumerDao userDao;
    @Autowired
    private SmsCodeDao smsCodeDao;

    @Override
    public int save(Consumer user) {
        initConsumer(user);
        return  userDao.save(user);
    }

    private void initConsumer(Consumer user) {
        user.setLevel(0);
        user.setCode("0");
        user.setState(0);
    }

    @Override
    public List<Consumer> findByCondition(Consumer obj) {
        return  userDao.findByCondition(obj);
    }

    @Override
    public List<Consumer> findByCondition(Consumer obj, int start, int limit) {
        return userDao.findByCondition(obj,start,limit);
    }

    @Override
    public Consumer findByPK(long pk) {
        return userDao.findByPK(pk,Consumer.class);
    }

    @Override
    public void update(Consumer user) {
        userDao.update(user);
    }

    @Override
    public void delete(Long pk) {
        userDao.delete(pk,Consumer.class);
    }

    @Override
    public Integer getTotalCount(Consumer user) {
        return userDao.getTotalCount(user);
    }

    @Override
    public boolean register(Map<String,String> param) {
        /**
         * 先获取验证码
         */
        String smsCode = param.get("smsCode");
        String mob = param.get("mob");
        SmsCode sc = new SmsCode();
        try {
            sc.setCode(smsCode);
        }catch (Exception e){
            return false;
        }
        sc.setMobile(mob);
        List<SmsCode> result = smsCodeDao.findByCondition(sc);
        if(result.size()>0){
            Consumer consumer = BeanUtils.mapToObj(param, Consumer.class);
            save(consumer);
            return  true;
        }
        return  false;
    }

}
