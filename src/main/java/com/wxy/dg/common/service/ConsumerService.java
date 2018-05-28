package com.wxy.dg.common.service;

import com.wxy.dg.common.model.Consumer;

import java.util.List;
import java.util.Map;

/**
 * Created by test on 2016/12/2.
 */
public interface ConsumerService {

        public int save(Consumer object);

        public List<Consumer> findByCondition(Consumer obj);

        public List<Consumer> findByCondition(Consumer obj, int start, int limit);


        public Consumer findByPK(long pk);

        public void update(Consumer user);

        public void delete(Long pk);


        public Integer getTotalCount(Consumer user);

        public boolean register(Map<String, String> param);

    }
