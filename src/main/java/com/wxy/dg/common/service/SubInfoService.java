package com.wxy.dg.common.service;

import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.model.SubInfo;

import java.util.Date;
import java.util.List;

/**
 * Created by test on 2016/12/2.
 */
public interface SubInfoService {

    public int save(SubInfo order);

    public List<SubInfo> findByCondition(SubInfo subInfo);

    public List<SubInfo> findByCondition(SubInfo order, int start, int limit);

    public List<SubInfo> getUnHandleSubInfo(int offset, int limit);

    public Long getUnHandleSubInfoCount();

    public SubInfo findByPK(long pk);

    public void update(SubInfo subInfo);

    public void update(List<Long> ids);

    public void delete(Long pk);


    public Integer getTotalCount(SubInfo subInfo);

    public void updateByState(List<SubInfo> list);

    public void initNextRound(String sid,String nextHandleTime);

    public void endSubInfo(String sid);

    /**
     * 根据提交时间计算下轮任务的推送时间
     * @param subTime
     * @return
     */
    public String getHandleTimeBySubTime(Date subTime);

    /**
     * 获取当天定时任务处理的企业品牌的数量
     * @param date
     * @return
     */
    public List<SubInfo> getScheduleSubInfoByDate(String date);


}
