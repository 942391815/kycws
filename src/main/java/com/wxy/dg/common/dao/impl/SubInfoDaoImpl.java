package com.wxy.dg.common.dao.impl;

import com.wxy.dg.common.dao.OrderDao;
import com.wxy.dg.common.dao.SubInfoDao;
import com.wxy.dg.common.model.Order;
import com.wxy.dg.common.model.SubInfo;
import com.wxy.dg.modules.dao.MyBatisBaseDaoImpl;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/3.
 */
@Component
public class SubInfoDaoImpl extends MyBatisBaseDaoImpl<SubInfo, Long> implements SubInfoDao {

    @Override
    public void updateByState(SubInfo subInfo) {
        this.getSqlSessionTemplate().update("com.wxy.dg.common.model.SubInfo.updateByState",subInfo);
    }

    @Override
    public List<SubInfo> getUnHandleSubInfo(int offset, int limit) {

        SqlSession session = this.getSqlSessionFactory().openSession();
        RowBounds rb = new RowBounds(offset, limit);
        return  session.selectList("com.wxy.dg.common.model.SubInfo.getUnHandleSubInfo",rb);
    }

    @Override
    public Long getUnHandleSubInfoCount() {
        return this.getSqlSessionTemplate().selectOne("com.wxy.dg.common.model.SubInfo.getUnHandleSubInfoCount");
    }

    @Override
    public Long getSubInfoCountByHandleTime(String date) {
        Map<String,String > map = new HashMap<String,String>();
        map.put("date",date);

        return this.getSqlSessionTemplate().selectOne("com.wxy.dg.common.model.SubInfo.getSubInfoCountByHandleTime",date);
    }

    @Override
    public List<SubInfo> getScheduleSubInfoByDate(String date) {
        Map<String,String > map = new HashMap<String,String>();
        map.put("date",date);

        return this.getSqlSessionTemplate().selectList("com.wxy.dg.common.model.SubInfo.getScheduleSubInfoByDate",date);
    }
}
