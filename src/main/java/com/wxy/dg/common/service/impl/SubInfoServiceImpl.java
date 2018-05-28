package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.dao.SubInfoDao;
import com.wxy.dg.common.enums.RoundEnum;
import com.wxy.dg.common.enums.SubInfoStateEnum;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.SubInfoService;
import com.wxy.dg.common.util.DateUtils;
import com.wxy.dg.common.util.UserUtils;
import com.wxy.dg.modules.security.SystemAuthorizingRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by test on 2016/12/2.
 */

@Service
@Transactional(readOnly = true)
public class SubInfoServiceImpl implements SubInfoService {
    @Autowired
    private SubInfoDao subInfoDao;

    @Override
    public int save(SubInfo subInfo) {
        subInfo.setState(0);
        subInfo.setUserId(0);
        subInfo.setRound(RoundEnum.FIRST.getCode());
        return subInfoDao.save(subInfo);
    }

    @Override
    public List<SubInfo> findByCondition(SubInfo subInfo) {
        return subInfoDao.findByCondition(subInfo);
    }

    @Override
    public List<SubInfo> findByCondition(SubInfo order, int start, int limit) {
        return subInfoDao.findByCondition(order, start, limit);
    }

    @Override
    public List<SubInfo> getUnHandleSubInfo(int offset, int limit) {
        return subInfoDao.getUnHandleSubInfo(offset, limit);
    }

    @Override
    public Long getUnHandleSubInfoCount() {
        return subInfoDao.getUnHandleSubInfoCount();
    }

    @Override
    public SubInfo findByPK(long pk) {
        return subInfoDao.findByPK(pk, SubInfo.class);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(SubInfo subInfo) {
        subInfoDao.update(subInfo);
    }

    @Override
    public void update(List<Long> ids) {
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();

        for (Long id : ids) {
            SubInfo subInfo = new SubInfo();
            subInfo.setState(1);
            subInfo.setSid(id);
            subInfo.setUserId(principal.getId());
            subInfoDao.update(subInfo);
        }
    }

    @Override
    public void delete(Long pk) {
        subInfoDao.delete(pk, SubInfo.class);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void updateByState(List<SubInfo> list) {
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();
        for (SubInfo each : list) {
            each.setState(1);
            each.setUserId(principal.getId());
            subInfoDao.updateByState(each);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void initNextRound(String sid,String nextHandleTime) {
        SubInfo result = subInfoDao.findByPK(Long.parseLong(sid), SubInfo.class);
        if (result != null) {
            SubInfo subInfo = new SubInfo();
            subInfo.setSid(Long.parseLong(sid));
            subInfo.setState(SubInfoStateEnum.NO_CLAIM.getCode());
            subInfo.setUserId(0);
            subInfo.setHandleTime(DateUtils.getDateTime());
            subInfo.setRound(result.getRound() + 1);
            subInfo.setNextHandleTime(nextHandleTime);
            subInfoDao.update(subInfo);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void endSubInfo(String sid) {
        SubInfo subInfo = new SubInfo();
        subInfo.setSid(Long.parseLong(sid));
        subInfo.setState(SubInfoStateEnum.DONE.getCode());
        subInfo.setHandleTime(DateUtils.getDateTime());

        subInfoDao.update(subInfo);
    }

    @Override
    public String getHandleTimeBySubTime(Date subTime) {
        String lastEightTime = DateUtils.getLastEightTime(0);//当天晚上8点
        Date date = DateUtils.stringToDate(lastEightTime);
        if(date.getTime()>subTime.getTime()){//八点之前提交的数据
            return DateUtils.setDateAndHour(1, 11);
        }else{//八点之后提交
            //第二天下午六点
            return  DateUtils.setDateAndHour(1, 18);
        }
    }

    @Override
    public List<SubInfo> getScheduleSubInfoByDate(String date) {
        return subInfoDao.getScheduleSubInfoByDate(date);
    }

    @Override
    public Integer getTotalCount(SubInfo subInfo) {
        return subInfoDao.getTotalCount(subInfo);
    }
}
