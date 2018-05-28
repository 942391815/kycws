package com.wxy.dg.common.dao.impl;

import com.wxy.dg.common.dao.ConsumerDao;
import com.wxy.dg.common.model.Consumer;
import com.wxy.dg.modules.dao.MyBatisBaseDaoImpl;
import org.springframework.stereotype.Component;

/**
 * Created by test on 2016/12/3.
 */

@Component
public class ConsumerDaoImpl extends MyBatisBaseDaoImpl<Consumer, Long> implements ConsumerDao {

}
