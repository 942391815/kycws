package com.wxy.dg.common.service.impl;

import com.wxy.dg.common.dao.BackInfoDao;
import com.wxy.dg.common.enums.SubInfoStateEnum;
import com.wxy.dg.common.model.BackInfo;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.common.service.BackInfoService;
import com.wxy.dg.common.service.SubInfoService;
import com.wxy.dg.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */

@Service
@Transactional(readOnly = true)
public class BackInfoServiceImpl implements BackInfoService {

    @Autowired
    private BackInfoDao backInfoDao;
    @Autowired
    private SubInfoService subInfoService;
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public int save(BackInfo backInfo) {
//        backInfo.setUpdateTime(DateUtils.getDateTime());
//        /**
//         * 更新sub_info表中的记录
//         */
//        SubInfo subInfo = new SubInfo();
//        subInfo.setSid(new Long(backInfo.getSubId()+""));
//        subInfo.setState(SubInfoStateEnum.PROCESSED.getCode());//已经处理完成
//        subInfoService.update(subInfo);
//        backInfo.setState(0);//定时任务未处理
        return backInfoDao.save(backInfo);
    }

    @Override
    public List<BackInfo> findByCondition(BackInfo backInfo) {
        return backInfoDao.findByCondition(backInfo);
    }

    @Override
    public List<BackInfo> findByCondition(BackInfo backInfo, int start, int limit) {
        return  backInfoDao.findByCondition(backInfo,start,limit);
    }

    @Override
    public BackInfo findByPK(long pk) {
        return backInfoDao.findByPK(pk,BackInfo.class);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation= Isolation.READ_COMMITTED)
    public void update(BackInfo backInfo) {
        backInfo.setUpdateTime(DateUtils.getDateTime());
        backInfoDao.update(backInfo);
    }

    @Override
    public void delete(Long pk) {
        backInfoDao.delete(pk,BackInfo.class);
    }

    @Override
    public Integer getTotalCount(BackInfo backInfo) {
        return backInfoDao.getTotalCount(backInfo);
    }

    @Override
    public List<Map<String, String>> sendMessage(List<String> list) {
        return backInfoDao.sendMessage(list);
    }

    @Override
    public List<Map<String, String>> getSendMessageTask(BackInfo backinfo) {
        return backInfoDao.getSendMessageTask(backinfo);
    }

    @Override
    public BackInfo getBackInfoBySubId(String subId) {

        return null;
    }

}
