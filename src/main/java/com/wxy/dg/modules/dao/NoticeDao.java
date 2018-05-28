package com.wxy.dg.modules.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wxy.dg.common.base.BaseDao;
import com.wxy.dg.modules.model.Notice;

@Repository
public class NoticeDao extends BaseDao<Notice> {
	
	// 获取每个用户的通知
	public List<Notice> findByUser(int userId) {
		List<Notice> list = find(
			"from Notice where objIds like ? order by send_time desc", "%," + String.valueOf(userId) + ",%");
		return list;
	}
}