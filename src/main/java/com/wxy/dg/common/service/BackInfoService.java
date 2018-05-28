package com.wxy.dg.common.service;

import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.common.model.Order;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */
public interface BackInfoService {

    public int save(BackInfo backInfo);

    public List<BackInfo> findByCondition(BackInfo backInfo);

    public List<BackInfo> findByCondition(BackInfo backInfo, int start, int limit);


    public BackInfo findByPK(long pk);

    public void update(BackInfo backInfo);

    public void delete(Long pk);


    public Integer getTotalCount(BackInfo backInfo);

    public List<Map<String,String>> sendMessage(List<String> list);

    public List<Map<String,String>> getSendMessageTask(BackInfo backinfo);

    public BackInfo getBackInfoBySubId(String subId);

}
