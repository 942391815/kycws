package com.wxy.dg.common.service;

import com.wxy.dg.common.enums.SubInfoEnum;

import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */
public interface UserTaskService {
    public SubInfoEnum submitTask(Map<String, String> map);

    /**
     * 消息推送时间是否合格
     * @param date
     * @return
     */
    public boolean isQualifiedEnterpriseSubmitTime(String date);
}
