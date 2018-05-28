package com.wxy.dg.common.dao;

import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.modules.dao.MyBatisBaseDao;

import java.util.List;

/**
 * Created by test on 2016/12/2.
 */
public interface SubInfoDao extends MyBatisBaseDao<SubInfo,Long>{
    public void updateByState(SubInfo subInfo);

    public List<SubInfo> getUnHandleSubInfo(int offset, int limit);

    public Long getUnHandleSubInfoCount();

    /**
     * 根据用户选择的定时任务处理时间
     * 查询当天需要处理的企业任务的数量
     * @param date 选择的定时任务处理时间
     * @return
     */
    public Long getSubInfoCountByHandleTime(String date);

    /**
     * 获取当天定时任务处理的企业品牌的数量
     * @param date
     * @return
     */
    public List<SubInfo> getScheduleSubInfoByDate(String date);

}
