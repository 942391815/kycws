package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.constant.Constant;
import com.wxy.dg.common.dao.SubInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by micheal on 2017/1/1.
 */

@Service
public class EnterpriseService {

    @Autowired
    private SubInfoDao subInfoDao;

    /**
     * 检查定时处理当天是否存在任务过多的情况
     * @param date
     * @return
     */
    public Long getSubInfoCountByHandleTime(String date){
        return subInfoDao.getSubInfoCountByHandleTime(date);
    }
}
