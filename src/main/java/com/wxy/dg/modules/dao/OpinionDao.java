package com.wxy.dg.modules.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.modules.model.Opinion;

@Repository
public class OpinionDao extends BaseDao<Opinion> {

	// 获取用户意见
	public List<Opinion> findByUser(int userId) {
		List<Opinion> list = find(
				"from Opinion where user.id = ? order by send_time desc", userId);
		return list;
	}
}