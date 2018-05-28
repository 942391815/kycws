package com.wxy.dg.common.dao.impl;

import com.wxy.dg.common.dao.BackInfoDao;
import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.modules.dao.MyBatisBaseDao;
import com.wxy.dg.modules.dao.MyBatisBaseDaoImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */
@Component
public class BackInfoDaoImpl extends MyBatisBaseDaoImpl<BackInfo, Long> implements BackInfoDao{
    @Override
    public List<Map<String, String>> sendMessage(List<String> param) {
        return this.getSqlSessionTemplate().selectList("com.wxy.dg.common.model.BackInfo.sendmessage",param);
    }

    @Override
    public List<Map<String, String>> getSendMessageTask(BackInfo backinfo) {
        return this.getSqlSessionTemplate().selectList("com.wxy.dg.common.model.BackInfo.getSendMessageTask",backinfo);
    }
}
