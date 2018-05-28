package com.wxy.dg.common.dao;

import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.modules.dao.MyBatisBaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */
public interface BackInfoDao extends MyBatisBaseDao<BackInfo, Long> {
    public List<Map<String,String>> sendMessage(List<String> list);

    public List<Map<String,String>> getSendMessageTask(BackInfo backinfo);


}
